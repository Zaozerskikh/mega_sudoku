package mega_sudoku.backend.dlx;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, отвечающий за работу и реализацию алгоритма "танцующих" ссылок.
 */
public class DancingLinks {
    private final ColumnNode header;

    private List<DancingNode> answer;

    /**
     * Счетчик решений для проверки единственности решения.
     */
    private int countSolutions = 0;

    private void search(int k) {
        if (header.right == header) {
            countSolutions++;
        } else {
            ColumnNode columnNode = selectColumnNodeHeuristic();
            columnNode.cover();

            for (DancingNode r = columnNode.down; r != columnNode; r = r.down) {
                answer.add(r);

                for (DancingNode j = r.right; j != r; j = j.right) {
                    j.column.cover();
                }
                search(k + 1);

                if (countSolutions > 1) {
                    return;
                }

                r = answer.remove(answer.size() - 1);
                columnNode = r.column;

                for (DancingNode j = r.left; j != r; j = j.left) {
                    j.column.uncover();
                }
            }
            columnNode.uncover();
        }
    }

    private ColumnNode selectColumnNodeHeuristic() {
        int min = Integer.MAX_VALUE;
        ColumnNode resultColumn = null;
        for (ColumnNode column = (ColumnNode) header.right; column != header; column = (ColumnNode) column.right) {
            if (column.size < min) {
                min = column.size;
                resultColumn = column;
            }
        }
        return resultColumn;
    }

    private ColumnNode makeDLXBoard(boolean[][] grid) {
        final int size = grid[0].length;

        ColumnNode headerNode = new ColumnNode();
        List<ColumnNode> columnNodes = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ColumnNode n = new ColumnNode();
            columnNodes.add(n);
            headerNode = (ColumnNode) headerNode.hookRight(n);
        }
        headerNode = headerNode.right.column;

        for (boolean[] aGrid : grid) {
            DancingNode prev = null;
            for (int j = 0; j < size; j++) {
                if (aGrid[j]) {
                    ColumnNode col = columnNodes.get(j);
                    DancingNode newNode = new DancingNode(col);
                    if (prev == null) {
                        prev = newNode;
                    }
                    col.up.hookDown(newNode);
                    prev = prev.hookRight(newNode);
                    col.size++;
                }
            }
        }

        headerNode.size = size;

        return headerNode;
    }

    DancingLinks(boolean[][] cover) {
        header = makeDLXBoard(cover);
    }

    public void runSolver() {
        answer = new LinkedList<>();
        search(0);
    }

    public boolean isOneSolution() {
        boolean isOneSol = countSolutions == 1;
        countSolutions = 0;
        return isOneSol;
    }
}

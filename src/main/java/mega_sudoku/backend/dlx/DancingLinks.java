package mega_sudoku.backend.dlx;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DancingLinks {
    private final ColumnNode header;

    private List<DancingNode> answer;

    /**
     * Счетчик решений для проверки единственности решения.
     */
    private int countSolutions = 0;

    private void search(int k) {
        if (header.R == header) {
            countSolutions++;
        } else {

            if (countSolutions > 1) { return; }

            ColumnNode c = selectColumnNodeHeuristic();
            c.cover();
            if (countSolutions > 1) { return; }
            for (DancingNode r = c.D; r != c; r = r.D) {
                answer.add(r);
                if (countSolutions > 1) { return; }
                for (DancingNode j = r.R; j != r; j = j.R) {
                    if (countSolutions > 1) { return; }
                    j.C.cover();
                }

                if (countSolutions > 1) { return; }
                search(k + 1);

                if (countSolutions > 1) { return; }
                r = answer.remove(answer.size() - 1);
                c = r.C;

                if (countSolutions > 1) { return; }
                for (DancingNode j = r.L; j != r; j = j.L) {
                    if (countSolutions > 1) { return; }
                    j.C.uncover();
                }
            }
            if (countSolutions > 1) { return; }
            c.uncover();
        }
    }

    private ColumnNode selectColumnNodeHeuristic() {
        int min = Integer.MAX_VALUE;
        ColumnNode ret = null;
        for (ColumnNode c = (ColumnNode) header.R; c != header; c = (ColumnNode) c.R) {
            if (c.size < min) {
                min = c.size;
                ret = c;
            }
        }
        return ret;
    }

    private ColumnNode makeDLXBoard(boolean[][] grid) {
        final int COLS = grid[0].length;

        ColumnNode headerNode = new ColumnNode("header");
        List<ColumnNode> columnNodes = new ArrayList<>();

        for (int i = 0; i < COLS; i++) {
            ColumnNode n = new ColumnNode(Integer.toString(i));
            columnNodes.add(n);
            headerNode = (ColumnNode) headerNode.hookRight(n);
        }
        headerNode = headerNode.R.C;

        for (boolean[] aGrid : grid) {
            DancingNode prev = null;
            for (int j = 0; j < COLS; j++) {
                if (aGrid[j]) {
                    ColumnNode col = columnNodes.get(j);
                    DancingNode newNode = new DancingNode(col);
                    if (prev == null)
                        prev = newNode;
                    col.U.hookDown(newNode);
                    prev = prev.hookRight(newNode);
                    col.size++;
                }
            }
        }

        headerNode.size = COLS;

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

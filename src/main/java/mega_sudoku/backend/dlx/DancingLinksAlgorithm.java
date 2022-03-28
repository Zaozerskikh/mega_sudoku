package mega_sudoku.backend.dlx;

import java.util.Arrays;

public class DancingLinksAlgorithm {
    private final int boardSize;
    private boolean isOnlyOneSolution;
    private final int subsectionSize;
    private final int NO_VALUE = -1;
    private final int CONSTRAINTS = 4;
    private final int minValue;
    private final int maxValue;
    private final int COVER_START_INDEX = 1;

    private final int[][] board;

    public DancingLinksAlgorithm(int[][] board) {
        int size = board.length;
        boardSize = size;
        subsectionSize = (int)Math.sqrt(size);
        maxValue = size;
        minValue = 1;
        this.board = board;
    }

    public void solve() {
        boolean[][] cover = initializeExactCoverBoard(board);
        DancingLinks dlx = new DancingLinks(cover);
        dlx.runSolver();
        isOnlyOneSolution = DancingLinks.isOneSolution();
    }

    // Узнаем, является ли решение единственным.
    public boolean getIfOnlyOneSolution() {
        return isOnlyOneSolution;
    }

    private int getIndex(int row, int column, int num) {
        return (row - 1) * boardSize * boardSize + (column - 1) * boardSize + (num - 1);
    }

    private boolean[][] createExactCoverBoard() {
        boolean[][] coverBoard = new boolean[boardSize * boardSize * maxValue][boardSize * boardSize * CONSTRAINTS];

        int hBase = 0;
        hBase = checkCellConstraint(coverBoard, hBase);
        hBase = checkRowConstraint(coverBoard, hBase);
        hBase = checkColumnConstraint(coverBoard, hBase);
        checkSubsectionConstraint(coverBoard, hBase);

        return coverBoard;
    }

    private int checkSubsectionConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= boardSize; row += subsectionSize) {
            for (int column = COVER_START_INDEX; column <= boardSize; column += subsectionSize) {
                for (int n = COVER_START_INDEX; n <= boardSize; n++, hBase++) {
                    for (int rowDelta = 0; rowDelta < subsectionSize; rowDelta++) {
                        for (int columnDelta = 0; columnDelta < subsectionSize; columnDelta++) {
                            int index = getIndex(row + rowDelta, column + columnDelta, n);
                            coverBoard[index][hBase] = true;
                        }
                    }
                }
            }
        }
        return hBase;
    }

    private int checkColumnConstraint(boolean[][] coverBoard, int hBase) {
        for (int column = COVER_START_INDEX; column <= boardSize; column++) {
            for (int n = COVER_START_INDEX; n <= boardSize; n++, hBase++) {
                for (int row = COVER_START_INDEX; row <= boardSize; row++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkRowConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= boardSize; row++) {
            for (int n = COVER_START_INDEX; n <= boardSize; n++, hBase++) {
                for (int column = COVER_START_INDEX; column <= boardSize; column++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkCellConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= boardSize; row++) {
            for (int column = COVER_START_INDEX; column <= boardSize; column++, hBase++) {
                for (int n = COVER_START_INDEX; n <= boardSize; n++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private boolean[][] initializeExactCoverBoard(int[][] board) {
        boolean[][] coverBoard = createExactCoverBoard();
        for (int row = COVER_START_INDEX; row <= boardSize; row++) {
            for (int column = COVER_START_INDEX; column <= boardSize; column++) {
                int n = board[row - 1][column - 1];
                if (n != NO_VALUE) {
                    for (int num = minValue; num <= maxValue; num++) {
                        if (num != n) {
                            Arrays.fill(coverBoard[getIndex(row, column, num)], false);
                        }
                    }
                }
            }
        }
        return coverBoard;
    }
}

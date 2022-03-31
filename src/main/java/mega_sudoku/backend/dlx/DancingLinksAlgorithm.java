package mega_sudoku.backend.dlx;

import java.util.Arrays;

public class DancingLinksAlgorithm {
    private final int boardSize;
    /**
     * Размер подполя.
     */
    private final int subsectionSize;
    private final int NO_VALUE = -1;

    /**
     * Количество ограничений для матрицы покрытия.
     */
    private final int CONSTRAINTS = 4;
    private final int minValue;
    private final int maxValue;
    private final int COVER_START_INDEX = 1;
    /**
     * Объект для взаимодействия с танцующими ссылками.
     */
    private final DancingLinks dlx;

    /**
     * Конструктор класса алгоритм с использованием танцующих ссылок для решения для конкретной доски.
     * @param board Конкретная доска.
     */
    public DancingLinksAlgorithm(int[][] board) {
        int size = board.length;
        boardSize = size;
        subsectionSize = (int)Math.sqrt(size);
        maxValue = size;
        minValue = 1;
        dlx = new DancingLinks(initializeExactCoverBoard(board));
    }

    public void solve() {
        dlx.runSolver();
    }

    /**
     * Узнаем информацию о единственности решения.
     * @return Единственное ли решение.
     */
    public boolean getIfOnlyOneSolution() {
        return dlx.isOneSolution();
    }

    /**
     * Метод получеия индекса элемента для матрицы покрытия по заданным входным параметрам.
     * @param row Ряд.
     * @param column Столбец.
     * @param num Номер.
     * @return Искомый индецс.
     */
    private int getIndex(int row, int column, int num) {
        return (row - 1) * boardSize * boardSize + (column - 1) * boardSize + (num - 1);
    }

    /**
     * Метод формирования матрицы покрытия.
     * @return Матрица покрытия.
     */
    private boolean[][] createExactCoverBoard() {
        boolean[][] coverBoard = new boolean[boardSize * boardSize * maxValue][boardSize * boardSize * CONSTRAINTS];

        int hBase = 0;
        hBase = checkCellConstraint(coverBoard, hBase);
        hBase = checkRowConstraint(coverBoard, hBase);
        hBase = checkColumnConstraint(coverBoard, hBase);
        checkSubsectionConstraint(coverBoard, hBase);

        return coverBoard;
    }

    /**
     * Проверка выполнений ограничений в подполе.
     * @param coverBoard Матрица покрытия.
     * @param hBase
     * @return
     */
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

    /**
     * Проверка выполнения ограничений в столбце.
     * @param coverBoard Матрица покртыия.
     * @param hBase
     * @return
     */
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

    /**
     * Проверка выполнения ограничений в ряду.
     * @param coverBoard Матрица покртыия.
     * @param hBase
     * @return
     */
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

    /**
     * Проверка выполнения ограничений в клетке.
     * @param coverBoard Матрица покрытия.
     * @param hBase
     * @return
     */
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

    /**
     * Инициализация матрицы покрытия для конкретного поля.
     * @param board Заданное поле.
     * @return Матрица покрытия.
     */
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

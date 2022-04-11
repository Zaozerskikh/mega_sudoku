package mega_sudoku.backend.dlx;

import java.util.Arrays;

public class DancingLinksAlgorithm {
    /**
     * Значение в пустой клетке.
     */
    private final int noValue;
    /**
     * Размер доски.
     */
    private final int boardSize;
    /**
     * Размер подполя.
     */
    private final int subsectionSize;
    /**
     * Минимальное значение в клетке.
     */
    private final int minValue;
    /**
     * Максимальное значение в клетке.
     */
    private final int maxValue;

    private final int coverStartIndex = 1;
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
        boardSize = maxValue = size;
        subsectionSize = (int)Math.sqrt(size);
        minValue = 1;
        noValue = -1;
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
        final int constraints = 4;
        boolean[][] coverBoard = new boolean[boardSize * boardSize * maxValue][boardSize * boardSize * constraints];

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
     */
    private void checkSubsectionConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = coverStartIndex; row <= boardSize; row += subsectionSize) {
            for (int column = coverStartIndex; column <= boardSize; column += subsectionSize) {
                for (int n = coverStartIndex; n <= boardSize; n++, hBase++) {
                    for (int rowDelta = 0; rowDelta < subsectionSize; rowDelta++) {
                        for (int columnDelta = 0; columnDelta < subsectionSize; columnDelta++) {
                            int index = getIndex(row + rowDelta, column + columnDelta, n);
                            coverBoard[index][hBase] = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Проверка выполнения ограничений в столбце.
     * @param coverBoard Матрица покртыия.
     */
    private int checkColumnConstraint(boolean[][] coverBoard, int hBase) {
        for (int column = coverStartIndex; column <= boardSize; column++) {
            for (int n = coverStartIndex; n <= boardSize; n++, hBase++) {
                for (int row = coverStartIndex; row <= boardSize; row++) {
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
     */
    private int checkRowConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = coverStartIndex; row <= boardSize; row++) {
            for (int n = coverStartIndex; n <= boardSize; n++, hBase++) {
                for (int column = coverStartIndex; column <= boardSize; column++) {
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
     */
    private int checkCellConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = coverStartIndex; row <= boardSize; row++) {
            for (int column = coverStartIndex; column <= boardSize; column++, hBase++) {
                for (int n = coverStartIndex; n <= boardSize; n++) {
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
        for (int row = coverStartIndex; row <= boardSize; row++) {
            for (int column = coverStartIndex; column <= boardSize; column++) {
                int n = board[row - 1][column - 1];
                if (n != noValue) {
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

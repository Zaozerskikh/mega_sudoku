package mega_sudoku.backend.sudoku;

import java.util.concurrent.ThreadLocalRandom;

public class SudokuGrid {
    private final int boardSize;

    private final int[][] table;

    public SudokuGrid(int size) {
        table = new int[size][];
        boardSize = (int)Math.sqrt(size);
        for (int i = 0; i < size; i++) {
            table[i] = new int[size];
            for (int j = 0; j < size; j++) {
                table[i][j] = (i * boardSize + i / boardSize + j) % (boardSize * boardSize) + 1;
            }
        }
    }

    private void transpose() {
        for (int i = 0; i < table.length; i++) {
            for (int j = i + 1; j < table[i].length; j++) {
                int tmp = table[i][j];
                table[i][j] = table[j][i];
                table[j][i] = tmp;
            }
        }
    }

    private void swapTwoArraysInTable(int a, int b) {
        for (int i = 0; i < table[a].length; i++) {
            int tmp = table[a][i];
            table[a][i] = table[b][i];
            table[b][i] = tmp;
        }
    }

    private void swapRows() {
        int area = ThreadLocalRandom.current().nextInt(0, boardSize);
        int line1 = ThreadLocalRandom.current().nextInt(0, boardSize);
        int N1 = area * boardSize + line1;
        int line2 = ThreadLocalRandom.current().nextInt(0, boardSize);
        while (line1 == line2) {
            line2 = ThreadLocalRandom.current().nextInt(0, boardSize);
        }
        int N2 = area * boardSize + line2;
        swapTwoArraysInTable(N1, N2);
    }

    private void swapColumns() {
        transpose();
        swapRows();
        transpose();
    }

    private void swapRowsArea() {
        int area1 = ThreadLocalRandom.current().nextInt(0, boardSize);
        int area2 = ThreadLocalRandom.current().nextInt(0, boardSize);
        while (area1 == area2) {
            area2 = ThreadLocalRandom.current().nextInt(0, boardSize);
        }

        for (int i = 0; i < boardSize; i++) {
            int N1 = area1 * boardSize + i;
            int N2 = area2 * boardSize + i;
            swapTwoArraysInTable(N1, N2);
        }
    }

    private void swapColumnsArea() {
        transpose();
        swapRowsArea();
        transpose();
    }

    /**
     * Метод, позволяющий рандомным образом переставить стандартную матрицу судоку.
     *
     * @param changesNumber Количество манипуляций для перестановки матрицы судоку.
     */
    public void mix(int changesNumber) {
        for (int i = 0; i < changesNumber; i++) {
            int id = ThreadLocalRandom.current().nextInt(0, 5);
            switch (id) {
                case (0) -> transpose();
                case (1) -> swapRows();
                case (2) -> swapColumns();
                case (3) -> swapRowsArea();
                case (4) -> swapColumnsArea();
            }
        }
    }

    public int[][] getMixedGrid(int changesNumber) {
        mix(changesNumber);
        return table;
    }
}
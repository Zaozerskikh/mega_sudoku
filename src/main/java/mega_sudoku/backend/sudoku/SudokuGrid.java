package mega_sudoku.backend.sudoku;

import java.util.concurrent.ThreadLocalRandom;

public class SudokuGrid {

    /**
     * Количество перестановок.
     */
    private static final int CHANGES_NUMBER = 10000;

    /**
     * Размер поля.
     */
    private final int boardSize;

    /**
     * Изначально правильно выстроенное поле.
     */
    private final int[][] table;

    /**
     * Конструктор, создающий изначально правильно выстроенное поля для дальнейших перестановок.
     * @param boardSize Размер поля.
     */
    public SudokuGrid(int boardSize) {
        table = new int[boardSize][boardSize];
        this.boardSize = (int)Math.sqrt(boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                table[i][j] = (i * this.boardSize + i / this.boardSize + j) % (this.boardSize * this.boardSize) + 1;
            }
        }
    }

    /**
     * Метод транспонирования.
     */
    private void transpose() {
        for (int i = 0; i < table.length; i++) {
            for (int j = i + 1; j < table[i].length; j++) {
                int tmp = table[i][j];
                table[i][j] = table[j][i];
                table[j][i] = tmp;
            }
        }
    }

    /**
     * Метод замены двух рядов в таблице.
     * @param firstRowIndex Номер первого ряда.
     * @param secondRowIndex Номер второго ряда.
     */
    private void swapTwoArraysInTable(int firstRowIndex, int secondRowIndex) {
        int[] tmp = new int[table[firstRowIndex].length];
        System.arraycopy(table[firstRowIndex], 0, tmp, 0, tmp.length);
        System.arraycopy(table[secondRowIndex], 0, table[firstRowIndex], 0, table[firstRowIndex].length);
        System.arraycopy(tmp, 0, table[secondRowIndex], 0, table[secondRowIndex].length);
    }

    /**
     * Замена двух рандомных рядов.
     */
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

    /**
     * Метод замены двух столбцов.
     */
    private void swapColumns() {
        transpose();
        swapRows();
        transpose();
    }

    /**
     * Метод замены группы рядов.
     */
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

    /**
     * Метод замены группы столбцов.
     */
    private void swapColumnsArea() {
        transpose();
        swapRowsArea();
        transpose();
    }

    /**
     * Метод, позволяющий рандомным образом переставить стандартную матрицу судоку.
     */
    private void mix() {
        for (int i = 0; i < CHANGES_NUMBER; i++) {
            int choose = ThreadLocalRandom.current().nextInt(0, 5);
            switch (choose) {
                case 0 -> transpose();
                case 1 -> swapRows();
                case 2 -> swapColumns();
                case 3 -> swapRowsArea();
                case 4 -> swapColumnsArea();
            }
        }
    }

    /**
     * Получение рандомной решенной судоку.
     * @return Рандомная решенная судоку.
     */
    public int[][] getMixedGrid() {
        mix();
        return table;
    }
}
package com.example.mega_sudoku.backend;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс, отвечающий за генерацию судоку.
 */
public class SudokuBuilder {
    // Простейшая реализация синглетона.
    private static SudokuBuilder INSTANCE;

    private SudokuBuilder() { }

    public static SudokuBuilder getSudokuBuilder() {
        if (INSTANCE == null) {
            INSTANCE = new SudokuBuilder();
        }
        return INSTANCE;
    }

    // Сгенерированная судоку.
    private Sudoku generatedSudoku;

    /**
     * Генерация судоку с заданными параметрами.
     * @param boardSize размер судоку.
     * @param diffLevel уровень сложности судоку.
     */
    public void generateSudoku(int boardSize, int diffLevel) {
        generatedSudoku = new Sudoku(
                this.generateProblem(boardSize, diffLevel),
                this.generateSolution(boardSize, diffLevel), diffLevel);
    }

    /**
     * Получение сгенерированной судоку.
     * @return сгенерированная судоку.
     */
    public Sudoku getGeneratedSudoku() {
        return generatedSudoku;
    }

    private int[][] generateProblem(int boardSize, int diffLevel) {
        // Временная затычка. Удалить после подключения алгоса dlx.
        int[][] problem = new int[boardSize][boardSize];
        //TODO implement problem generation.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                problem[i][j] = -1;
                if (problem[i][j] == 0) {
                    problem[i][j]++;
                }
            }
        }
        return problem;
    }

    private int[][] generateSolution(int boardSize, int diffLevel) {

        // Временная затычка. Удалить после подключения алгоса dlx.
        int[][] solution = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                solution[i][j] = ThreadLocalRandom.current().nextInt(2, 15);
            }
        }

        //TODO implement solution generation.
        return solution;
    }
}

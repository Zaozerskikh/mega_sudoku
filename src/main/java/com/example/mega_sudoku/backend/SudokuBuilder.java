package com.example.mega_sudoku.backend;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс, отвечающий за генерацию судоку.
 */
public class SudokuBuilder {
    // Простейшая реализация синглетона.
    private static SudokuBuilder INSTANCE;

    private int[][] problem;

    private int[][] solution;

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
        int[][] sol = this.generateSolution(boardSize, diffLevel);
        int[][] pr = this.generateProblem(boardSize, diffLevel);
        generatedSudoku = new Sudoku(pr, sol, diffLevel);
    }

    /**
     * Получение сгенерированной судоку.
     * @return сгенерированная судоку.
     */
    public Sudoku getGeneratedSudoku() {
        return generatedSudoku;
    }

    private int[][] generateProblem(int boardSize, int diffLevel) {
        int[][] problem = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(solution[i], 0, problem[i], 0, boardSize);
        }

        for (int i = 0; i < 500; i++) {
            int n = ThreadLocalRandom.current().nextInt(0, boardSize);

            int m = ThreadLocalRandom.current().nextInt(0, boardSize);
            problem[n][m] = -1;
        }
        //DancingLinksAlgorithm dancingLinksAlgorithm = new DancingLinksAlgorithm(problem);
        //return DancingLinksAlgorithm.getSolution();
        this.problem = problem;
        return problem;
    }

    private int[][] generateSolution(int boardSize, int diffLevel) {
        Grid grid = new Grid(boardSize);
        grid.mix(10);
        solution = grid.getGrid();
        return grid.getGrid();
    }
}

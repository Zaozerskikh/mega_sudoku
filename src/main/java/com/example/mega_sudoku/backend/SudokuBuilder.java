package com.example.mega_sudoku.backend;

import java.util.concurrent.ThreadLocalRandom;

public class SudokuBuilder {
    public Sudoku generateSudoku(int boardSize, int diffLevel) {
        return new Sudoku(this.generateProblem(boardSize, diffLevel), this.generateSolution(boardSize, diffLevel), diffLevel);
    }

    private int[][] generateProblem(int boardSize, int diffLevel) {
        // Временная затычка. Удалить после подключения алгоса dlx.
        int[][] problem = new int[boardSize][boardSize];
        //TODO implement problem generation.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                problem[i][j] = ThreadLocalRandom.current().nextInt(-1, 3);
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
    };
}

package com.example.mega_sudoku.backend;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SudokuBuilder {
    public ArrayList<int[][]> generateProblemAndSolution(int boardSize, int diffLevel) {
        ArrayList<int[][]> problemAndSolution = new ArrayList<>();
        problemAndSolution.add(this.generateProblem(boardSize, diffLevel));
        problemAndSolution.add(this.generateSolution(boardSize, diffLevel));
        return problemAndSolution;
    }

    private int[][] generateProblem(int boardSize, int diffLevel) {
        int[][] problem = new int[boardSize][boardSize];
        //TODO implement problem generation.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                problem[i][j] = ThreadLocalRandom.current().nextInt(-1, 3);
            }
        }
        return problem;
    };

    private int[][] generateSolution(int boardSize, int diffLevel) {
        int[][] solution = new int[boardSize][boardSize];
        //TODO implement solution generation.
        return solution;
    };
}

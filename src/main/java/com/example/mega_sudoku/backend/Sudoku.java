package com.example.mega_sudoku.backend;

public class Sudoku {
    // Судоку (задача).
    private final int[][] problem;

    // Судоку (решение).
    private final int[][] solution;

    // Текущая позиция на доске.
    private int[][] currentPosition;

    // Уровень сложности.
    private final int diffLevel;

    public void updateCurrentPosition(int[][] newCurrentPosition) {
        currentPosition = newCurrentPosition;
    }

    public int[][] getProblem() {
        return this.problem;
    }

    public int[][] getSolution() {
        return this.solution;
    }

    public int[][] getCurrentPosition() {
        return this.currentPosition;
    }

    public int getBoardSize() {
        return problem.length;
    }

    public int getDiffLevel() {
        return diffLevel;
    }

    public Sudoku(int[][] problem, int[][] solution, int diffLevel) {
        this.problem = problem;
        this.solution = solution;
        this.currentPosition = problem;
        this.diffLevel = diffLevel;
    }
}

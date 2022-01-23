package com.example.mega_sudoku.backend;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс реализующий головоломку.
 */
public class Sudoku implements Serializable {
    // Поле для работы сериализации.
    @Serial
    private static final long serialVersionUID = 1L;

    // Судоку (задача).
    private final int[][] problem;

    // Судоку (решение).
    private final int[][] solution;

    // Текущая позиция на доске.
    private int[][] currentPosition;

    // Уровень сложности.
    private final int diffLevel;

    public void updateCurrentPosition(int value, int i, int j) {
        currentPosition[i][j] = value;
    }

    public void setCurrentPosition(int[][] newPosition) {
        currentPosition = newPosition;
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

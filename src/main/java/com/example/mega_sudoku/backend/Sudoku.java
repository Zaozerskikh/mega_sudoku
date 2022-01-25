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

    /**
     * Обновление текущей позиции на доске.
     * @param value новое значение клетки.
     * @param i координата i клетки.
     * @param j координата J клетки.
     */
    public void updateCurrentPosition(int value, int i, int j) {
        currentPosition[i][j] = value;
    }

    /**
     * Замена текущий позиции на доске (используется для сброса головоломки к изначальной).
     * @param newPosition новая текущая позиция на доске.
     */
    public void setCurrentPosition(int[][] newPosition) {
        currentPosition = newPosition;
    }

    /**
     * Getter для problem.
     * @return problem.
     */
    public int[][] getProblem() {
        return this.problem;
    }

    /**
     * Getter для solution.
     * @return solution.
     */
    public int[][] getSolution() {
        return this.solution;
    }

    /**
     * Getter для currentPosition.
     * @return currentPosition.
     */
    public int[][] getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Возвращает размеры поля данного судоку.
     * @return размер поля.
     */
    public int getBoardSize() {
        return problem.length;
    }

    /**
     * Getter для diffLevel.
     * @return уровень сложности судоку.
     */
    public int getDiffLevel() {
        return diffLevel;
    }

    /**
     * Конструктор судоку.
     * @param problem судоку (задача).
     * @param solution судоку (решение).
     * @param diffLevel уровень сложности судоку.
     */
    public Sudoku(int[][] problem, int[][] solution, int diffLevel) {
        this.problem = problem;
        this.solution = solution;
        this.currentPosition = problem;
        this.diffLevel = diffLevel;
    }
}

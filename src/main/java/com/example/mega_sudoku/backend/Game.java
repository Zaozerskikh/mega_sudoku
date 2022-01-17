package com.example.mega_sudoku.backend;

public class Game {
    private int boardSize;
    private int diffLevel;
    private int[][] generatedSudoku;

    public int[][] getGeneratedSudoku() {
        return this.generatedSudoku;
    }

    public Game(int boardSize, int diffLevel) {
        this.boardSize = boardSize;
        this.diffLevel = diffLevel;
        this.generatedSudoku = generateSudoku(boardSize);
    }

    private int[][] generateSudoku(int boardSize) {
        int[][] sudoku = new int[boardSize][boardSize];
        return sudoku;
    }
}

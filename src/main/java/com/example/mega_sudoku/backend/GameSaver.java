package com.example.mega_sudoku.backend;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameSaver {
    public static void save(String key, int[][] currentPosition, int[][] solution) {

    }

    // Получает из таблицы двумерный массив значений.
    public static int[][] getCurrentPos(GridPane board, int boardSize) {
        int[][] currentPosition = new int[boardSize][boardSize];
        board.getChildren().forEach(x -> {
            if (x.getClass() == TextField.class) {
                if (((TextField)x).getText() != "") {
                    currentPosition[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] =
                            Integer.parseInt(((TextField)x).getText());
                } else {
                    currentPosition[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] = -1;
                }
            }
        });
        return currentPosition;
    }
}

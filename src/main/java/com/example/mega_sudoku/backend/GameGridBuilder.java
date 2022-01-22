package com.example.mega_sudoku.backend;

import com.example.mega_sudoku.frontend.GameController;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class GameGridBuilder {
    /**
     * Создает сетку игры по заданной судоку.
     * @param boardSize размер доски.
     * @param currentPosition судоку.
     * @return игровая сетка.
     */
    public GridPane buildGameGrid(int boardSize, int[][] currentPosition) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                TextField textField = new TextField();
                if (boardSize == 16) {
                    textField.setFont(new Font(16));
                } else {
                    textField.setFont(new Font(10));
                }
                textField.setFocusTraversable(false);
                if (currentPosition[i][j] != -1) {
                    textField.setText(Integer.toString(currentPosition[i][j]));
                    textField.setEditable(false);
                }
                textField.setOnMousePressed(mouseEvent -> GameController.checkAndUpdateCurrTF((TextField) mouseEvent.getSource()));
                gridPane.add(textField, i, j);
            }
        }

        // TODO Если будет время - заменить этот ужас нормальным кодом.
        String cellBorderColor = (ColorThemeManager.isDarkTheme()) ? "#ffffff" : "#464646";
        String cellBackgroundColor = (ColorThemeManager.isDarkTheme()) ? "#464646" : "#ffffff";
        gridPane.getChildren().forEach(x -> ((TextField)x).setPrefSize(720.0 / boardSize, 720.0 / boardSize));
        gridPane.getChildren().forEach(x -> {
            if (ColorThemeManager.isDarkTheme()) {
                x.setStyle("-fx-background-color: #525252; -fx-text-fill: white;");
            }
            if (GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getRowIndex(x) != 0 &&
                    (GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getColumnIndex(x) != 0)) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 4 1 1 4; -fx-border-color: " + cellBorderColor);
            } else {
                if (GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getRowIndex(x) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 4 1 1 1; -fx-border-color: " + cellBorderColor);
                }
                if (GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getColumnIndex(x) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 1 4; -fx-border-color: " + cellBorderColor);
                }
            }
        });
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 5 1 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == 0 && GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 5 1 1 4; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 5 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == boardSize - 1 && GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 5 4; -fx-border-color: " + cellBorderColor);
            }

            if (GridPane.getColumnIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 1 5; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 4 1 1 5; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 5 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 4 5 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 5 5; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 5 5 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 5 5 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 5 1 1 5; -fx-border-color: " + cellBorderColor);
            }
        });
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) != 0 && GridPane.getColumnIndex(x) != 0 &&
                    GridPane.getRowIndex(x) != boardSize - 1 && GridPane.getColumnIndex(x) != boardSize - 1 &&
                    GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) != 0 && GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + " ; -fx-border-style: solid solid solid solid; -fx-border-width: 1; -fx-border-color: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor);
            }
        });

        // END OF TODO
        return gridPane;
    }
}

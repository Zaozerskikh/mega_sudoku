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
        String cellBorderColor = (ColorThemeManager.isDarkTheme()) ? "#ffffff" : "#525252";
        String cellBackgroundColor = (ColorThemeManager.isDarkTheme()) ? "#525252" : "#ffffff";
        gridPane.getChildren().forEach(x -> ((TextField)x).setPrefSize(720.0 / boardSize, 720.0 / boardSize));
        gridPane.getChildren().forEach(x -> {
            if (ColorThemeManager.isDarkTheme()) {
                x.setStyle("-fx-background-color: #525252; -fx-text-fill: white");
            }
            if (GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getRowIndex(x) != 0 &&
                    (GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getColumnIndex(x) != 0)) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: solid hidden hidden solid; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            } else {
                if (GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getRowIndex(x) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: solid hidden hidden hidden; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
                }
                if (GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getColumnIndex(x) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: hidden hidden hidden solid; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
                }
            }
        });
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: solid hidden hidden hidden; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == 0 && GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: solid hidden hidden solid; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: hidden hidden solid hidden; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == boardSize - 1 && GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: hidden hidden solid solid; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }

            if (GridPane.getColumnIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: hidden hidden hidden solid; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: solid hidden hidden solid; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: hidden solid hidden hidden; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: solid solid hidden hidden; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: hidden hidden solid solid; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-style: hidden solid solid hidden; -fx-border-width: 3; -fx-border-color: " + cellBorderColor);
            }

        });
        // END OF TODO

        gridPane.setGridLinesVisible(true);
        gridPane.getStyleClass().add("myGridStyle");
        if (ColorThemeManager.isDarkTheme()) {
            gridPane.getStylesheets().add(this.getClass().getResource("/styles/dark_grid_design.css").toExternalForm());
        } else {
            gridPane.getStylesheets().add(this.getClass().getResource("/styles/white_grid_design.css").toExternalForm());
        }
        return gridPane;
    }
}

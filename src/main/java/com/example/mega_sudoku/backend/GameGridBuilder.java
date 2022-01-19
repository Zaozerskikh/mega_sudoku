package com.example.mega_sudoku.backend;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class GameGridBuilder {
    public GridPane buildGameGrid(int boardSize) {
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
                gridPane.add(textField, i, j);
            }
        }
        gridPane.getChildren().forEach(x -> ((TextField)x).setPrefSize(720.0 / boardSize, 720.0 / boardSize));
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getRowIndex(x) != 0 &&
                    (GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getColumnIndex(x) != 0)) {
                x.setStyle("-fx-border-style: solid hidden hidden solid; -fx-border-width: 3; -fx-border-color: #000000;");
            } else {
                if (GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getRowIndex(x) != 0) {
                    x.setStyle("-fx-border-style: solid hidden hidden hidden; -fx-border-width: 3; -fx-border-color: #000000;");
                }
                if (GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getColumnIndex(x) != 0) {
                    x.setStyle("-fx-border-style: hidden hidden hidden solid; -fx-border-width: 3; -fx-border-color: #000000;");
                }
            }
        });
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-border-style: solid hidden hidden hidden; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getRowIndex(x) == 0 && GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-border-style: solid hidden hidden solid; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getRowIndex(x) == boardSize - 1 && GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-border-style: hidden hidden solid solid; -fx-border-width: 3; -fx-border-color: #000000;");
            }

            if (GridPane.getColumnIndex(x) == 0) {
                x.setStyle("-fx-border-style: hidden hidden hidden solid; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-border-style: solid hidden hidden solid; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1) {
                x.setStyle("-fx-border-style: hidden solid hidden hidden; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0) {
                x.setStyle("-fx-border-style: solid solid hidden hidden; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-border-style: hidden hidden solid solid; -fx-border-width: 3; -fx-border-color: #000000;");
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-border-style: hidden solid solid hidden; -fx-border-width: 3; -fx-border-color: #000000;");
            }
        });
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }
}

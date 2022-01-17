package com.example.mega_sudoku.backend;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    public Stage generateGameStage()  {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                TextField textField = new TextField();
                if (boardSize == 4) {
                    textField.setFont(new Font(18));
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
                x.setStyle("-fx-border-style: solid hidden hidden solid; -fx-border-width: 3; -fx-border-color: #c41492;");
            } else {
                if (GridPane.getRowIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getRowIndex(x) != 0) {
                    x.setStyle("-fx-border-style: solid hidden hidden hidden; -fx-border-width: 3; -fx-border-color: #c41492;");
                }
                if (GridPane.getColumnIndex(x) % (int)Math.sqrt(boardSize) == 0 && GridPane.getColumnIndex(x) != 0) {
                    x.setStyle("-fx-border-style: hidden hidden hidden solid; -fx-border-width: 3; -fx-border-color: #c41492;");
                }
            }
        });
        gridPane.setStyle("-fx-border-width: 3; -fx-border-color: #c41492;");
        gridPane.setGridLinesVisible(true);
        Pane root = new Pane(gridPane);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 900, 730));
        return stage;
    }
}

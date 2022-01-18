package com.example.mega_sudoku.backend;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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

    public Stage generateGameStage() throws IOException {
        GridPane gridPane = new GameGridBuilder().buildGameGrid(boardSize);
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml_stages/game_screen.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        GridPane paneForTable = new GridPane();
        paneForTable.add(gridPane, 0, 0);
        gridPane.setPadding(new Insets(11));
        Stage stage = new Stage();
        stage.setScene(new Scene(new HBox(paneForTable, root), 870, 730));
        return stage;
    }
}

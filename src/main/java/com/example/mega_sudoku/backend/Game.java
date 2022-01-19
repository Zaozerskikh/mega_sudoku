package com.example.mega_sudoku.backend;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Game {
    private int boardSize;
    private int diffLevel;
    private int[][] problem;
    private int[][] solution;

    public Game(int boardSize, int diffLevel) {
        this.boardSize = boardSize;
        this.diffLevel = diffLevel;
        var problemAndSolution = new SudokuBuilder().generateProblemAndSolution(boardSize, diffLevel);
        this.problem = problemAndSolution.get(0);
        this.solution = problemAndSolution.get(1);
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
        stage.getScene().getStylesheets().addAll(this.getClass().getResource("/styles/game_buttons_design.css").toExternalForm());
        stage.setTitle("Мега-Cудоку " + boardSize + " x " + boardSize);
        return stage;
    }
}

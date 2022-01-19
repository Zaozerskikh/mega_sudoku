package com.example.mega_sudoku.backend;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    //Размер игрового поля.
    private final int boardSize;

    // Уровень сложности.
    private final int diffLevel;

    // Игровое поле.
    private GridPane gameGrid;

    // Судоку (задача).
    private final int[][] problem;

    // Судоку (решенная).
    private final int[][] solution;

    // Сохранено ли текущее состояние игры.
    private boolean saved;

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public Game(int boardSize, int diffLevel) {
        this.boardSize = boardSize;
        this.diffLevel = diffLevel;
        var problemAndSolution = new SudokuBuilder().generateProblemAndSolution(boardSize, diffLevel);
        this.problem = problemAndSolution.get(0);
        this.solution = problemAndSolution.get(1);
        this.saved = false;
    }

    public Stage generateGameStage() throws IOException {
        gameGrid = new GameGridBuilder().buildGameGrid(boardSize, problem);
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml_stages/game_screen.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        GridPane paneForTable = new GridPane();
        paneForTable.add(gameGrid, 0, 0);
        gameGrid.setPadding(new Insets(11));
        Stage stage = new Stage();
        stage.setScene(new Scene(new HBox(paneForTable, root), 870, 730));
        stage.getScene().getStylesheets().addAll(this.getClass().getResource("/styles/game_buttons_design.css").toExternalForm());
        stage.setTitle("Мега-Cудоку " + boardSize + " x " + boardSize);
        stage.setResizable(false);
        return stage;
    }

    public void showSolution() {
        gameGrid.getChildren().forEach(x -> {
            if(x.getClass() == TextField.class) {
                if (problem[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] == -1) {
                    ((TextField)x).setText(Integer.toString(solution[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)]));
                }
            }
        });
    }

    public void showTip(TextField textField) {
        if (textField.editableProperty().getValue()) {
            textField.setText(Integer.toString(solution[GridPane.getColumnIndex(textField)][GridPane.getRowIndex(textField)]));
            textField.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    textField.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                    timer.cancel();
                }
            }, 5*1000);
        }
    }

    public String check() {
        var currentPosition = GameSaver.getCurrentPos(gameGrid, boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (currentPosition[i][j] == -1) {
                    return "empty_cell";
                }
            }
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (currentPosition[i][j] != solution[i][j]) {
                    return "incorrect";
                }
            }
        }
        return "correct";
    }

    public void reset() {
        gameGrid.getChildren().forEach(x -> {
            if (x.getClass() == TextField.class) {
                if (problem[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] == -1) {
                    ((TextField)x).setText("");
                }
            }
        });
    }
}

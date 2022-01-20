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
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    // Игровое поле.
    private GridPane gameGrid;

    // Судоку.
    private final Sudoku sudoku;

    // Сохранено ли текущее состояние игры.
    private boolean saved;

    /**
     * Проверка сохранено ли текущее состояние игры.
     * @return результат проверки.
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Установка сохранения.
     * @param saved текущее состояние (сохранено или нет).
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public Game(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.saved = false;
    }

    /**
     * Генерация окна игры.
     * @return окно игры.
     * @throws IOException не выкидывается.
     */
    public Stage generateGameStage() throws IOException {
        gameGrid = new GameGridBuilder().buildGameGrid(sudoku.getBoardSize(), sudoku.getCurrentPosition());
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
        String diffInfo = (sudoku.getDiffLevel() == 1) ? "Простой" : (sudoku.getDiffLevel() == 2) ? "Средний" : "Сложный";
        stage.setTitle("Мега-Cудоку " + sudoku.getBoardSize() + " x " + sudoku.getBoardSize() + " " + diffInfo);
        stage.setResizable(false);
        return stage;
    }

    /**
     * Отображает правильное решение.
     */
    public void showSolution() {
        gameGrid.getChildren().forEach(x -> {
            if(x.getClass() == TextField.class) {
                if (sudoku.getProblem()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] == -1) {
                    ((TextField)x).setText(Integer.toString(sudoku.getSolution()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)]));
                }
            }
        });
    }

    /**
     * Отображает подсказку.
     * @param textField клетка в которой нужно отобразить подсказку.
     */
    public void showTip(TextField textField) {
        if (textField.editableProperty().getValue()) {
            textField.setText(Integer.toString(sudoku.getSolution()[GridPane.getColumnIndex(textField)][GridPane.getRowIndex(textField)]));
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

    /**
     * Проверка на корректность решения пользователя.
     * @return статус верно ли решена головоломка.
     */
    public String checkAnswer() {
        for (int i = 0; i < sudoku.getBoardSize(); i++) {
            for (int j = 0; j < sudoku.getBoardSize(); j++) {
                if (sudoku.getCurrentPosition()[i][j] == -1) {
                    return "empty_cell";
                }
            }
        }
        for (int i = 0; i < sudoku.getBoardSize(); i++) {
            for (int j = 0; j < sudoku.getBoardSize(); j++) {
                if (sudoku.getCurrentPosition()[i][j] != sudoku.getSolution()[i][j]) {
                    return "incorrect";
                }
            }
        }
        return "correct";
    }

    /**
     * Сброс головоломки до начального этапа (удаление пользовательского решения).
     */
    public void reset() {
        gameGrid.getChildren().forEach(x -> {
            if (x.getClass() == TextField.class) {
                if (sudoku.getProblem()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] == -1) {
                    ((TextField)x).setText("");
                }
            }
        });
    }

    public Sudoku getSudoku() {
        return this.sudoku;
    }


    public void updateCurrentPosition(TextField currentTextField) {
        sudoku.updateCurrentPosition(Integer.parseInt(currentTextField.getText()), GridPane.getColumnIndex(currentTextField), GridPane.getRowIndex(currentTextField));
    }
}

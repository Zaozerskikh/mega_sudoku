package com.example.mega_sudoku.backend;

import com.example.mega_sudoku.frontend.StartController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс реализующий основную механику игры.
 */
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
     * Getter для sudoku.
     * @return sudoku.
     */
    public Sudoku getSudoku() {
        return this.sudoku;
    }

    /**
     * Установка сохранения.
     * @param saved текущее состояние (сохранено или нет).
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * Конструктор игры.
     * @param sudoku судоку для данной игры.
     */
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
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(StartController.class.getResource("/fxml_stages/game_screen.fxml"))));
        gameGrid.setPadding(new Insets(11));
        ((GridPane)(scene.getRoot().getChildrenUnmodifiable().get(0))).add(gameGrid, 0, 0);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        ColorThemeManager.setThemeToScene(stage.getScene(),
                this.getClass().getResource("/styles/dark_game_screen.css").toExternalForm(),
                this.getClass().getResource("/styles/white_game_screen.css").toExternalForm());
        String diffInfo = (sudoku.getDiffLevel() == 1) ? "Простой" : (sudoku.getDiffLevel() == 2) ? "Средний" : "Сложный";
        ((Label)((GridPane)scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(4)).
                setText("Мега-Cудоку " + sudoku.getBoardSize() + " x " + sudoku.getBoardSize() + " " + diffInfo);
        stage.getIcons().add(new Image("/icon.png"));
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
        sudoku.setCurrentPosition(sudoku.getSolution());
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
                    if (ColorThemeManager.isDarkTheme()) {
                        textField.setBackground(new Background(new BackgroundFill(Color.valueOf("#525252"), null, null)));
                    } else {
                        textField.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                    }
                    timer.cancel();
                }
            }, 3*1000);
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
        sudoku.setCurrentPosition(sudoku.getProblem());
    }

    /**
     * Обновление текущей позиции на доске.
     * @param currentTextField клетка в которой изменилось значение.
     */
    public void updateCurrentPosition(TextField currentTextField) {
        sudoku.updateCurrentPosition(Integer.parseInt(currentTextField.getText()), GridPane.getColumnIndex(currentTextField), GridPane.getRowIndex(currentTextField));
    }

    /**
     * Изменение размера игровой доски.
     * @param stageHeight текущая высота окна с игрой.
     */
    public void resizeBoard(double stageHeight) {
        gameGrid.getChildren().forEach(x -> {
            ((TextField)x).setPrefSize((stageHeight - Math.sqrt(sudoku.getBoardSize())) / sudoku.getBoardSize(), (stageHeight - 52) / sudoku.getBoardSize());
            ((TextField)x).setFont(new Font((int)(stageHeight - 52) / (3 * sudoku.getBoardSize())));
        });
    }
}

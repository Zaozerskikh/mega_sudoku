package mega_sudoku.frontend.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mega_sudoku.backend.game.GameGridBuilder;
import mega_sudoku.backend.models.GameCheckResult;
import mega_sudoku.backend.models.GameModel;
import mega_sudoku.backend.utils.ColorThemeManager;
import mega_sudoku.backend.utils.Dialog;
import mega_sudoku.backend.utils.DualogType;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс, определяющий представление текущей игры.
 */
public class GameView {

    // Объект модели данного представления
    protected final GameModel gameModel;

    // Игровое поле.
    @FXML
    protected final GridPane gameBoard;

    /**
     * Конструктор, в котором происходит инициализация модели и игровой доски.
     */
    public GameView() {
        gameModel = new GameModel(this);
        gameBoard = GameGridBuilder.getBuilder().getGeneratedPane();
    }

    /**
     * Получение текущего активного окна приложения.
     * @return текущее активное окно приложения.
     */
    protected Stage getCurrentStage() {
        return (Stage)gameBoard.getScene().getWindow();
    }

    /**
     * Показ подсказки игроку.
     * @param selectedField клетка, в которой нужно показать число.
     * @param value само число.
     */
    public void showTip(TextField selectedField, int value) {
        selectedField.setText(Integer.toString(value));
        selectedField.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ColorThemeManager.isDarkTheme()) {
                    selectedField.setBackground(new Background(new BackgroundFill(Color.valueOf("#464646"), null, null)));
                } else {
                    selectedField.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                }
                timer.cancel();
            }
        }, 3*1000);
    }

    /**
     * Показ решения.
     * @param solution решение, которое необходимо отобразить.
     */
    public void showSolution(int[][] solution) {
        this.gameBoard.getChildren().forEach(x -> ((TextField)x).setText(Integer.toString(solution[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)])));
    }

    /**
     * Отображение позиции на доске.
     * @param problem позиция, которую необходимо отобразить.
     */
    public void resetPosition(int[][] problem) {
        gameBoard.getChildren().forEach(x -> {
            if (problem[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] == -1) {
                ((TextField)x).setText("");
            }
        });
    }

    /**
     * Изменение размера игровой доски.
     * @param stageHeight текущая высота окна с игрой.
     * @param boardSize размер доски текущего судоку.
     * @param controls массив кнопок, размер которых необходимо изменить.
     */
    @SuppressWarnings("all")
    public void resizeStage(double stageHeight, int boardSize, Button[] controls) {
        gameBoard.getChildren().forEach(x -> {
            ((TextField)x).setPrefSize((stageHeight - Math.sqrt(boardSize)) / boardSize, (stageHeight - 52) / boardSize);
            ((TextField)x).setFont(new Font((int)(stageHeight - 52) / (3 * boardSize) - 1));
            if (GridPane.getRowIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 || GridPane.getRowIndex(x) == boardSize - 1) {
                ((TextField)x).setPrefHeight((stageHeight - 52) / boardSize + 5);
            }
            if (GridPane.getColumnIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 || GridPane.getColumnIndex(x) == boardSize - 1) {
                ((TextField) x).setPrefWidth((stageHeight - 52) / boardSize + 7);
            }
        });
        if (getCurrentStage().isFullScreen()) {
            Arrays.stream(controls).toList().forEach(x -> x.setMinSize((stageHeight - 172) / 6, (stageHeight - 172) / 6));
            Arrays.stream(controls).toList().forEach(x -> x.setMaxSize((stageHeight - 172) / 6, (stageHeight - 172) / 6));
        } else {
            Arrays.stream(controls).toList().forEach(x -> x.setMaxSize(50, 50));
            Arrays.stream(controls).toList().forEach(x -> x.setMinSize(50, 50));
        }
    }

    /**
     * Показ информационного сообщения об ошибочном значении клетки игрового поля.
     * @param selectedField клетка с ошибочным значением.
     */
    public void showErrorByIncorrectCellValue(TextField selectedField) {
        Dialog dialog = new Dialog(DualogType.ERROR, "Ошибка!", "\nНекорректное значение поля.", getCurrentStage());
        dialog.showDialog();
        selectedField.setFocusTraversable(true);
        selectedField.selectAll();
        selectedField.requestFocus();
        selectedField.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ColorThemeManager.isDarkTheme()) {
                    selectedField.setBackground(new Background(new BackgroundFill(Color.valueOf("#464646"), null, null)));
                } else {
                    selectedField.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                }
                timer.cancel();
            }
        }, 5*1000);
    }

    /**
     * Показ результата проверки решения пользователя.
     * @param result результат.
     */
    public void displayCheckAnswerResult(GameCheckResult result) {
        switch (result) {
            case EMPTY_CELLS -> {
                Dialog dialog = new Dialog(DualogType.ERROR, "Судоку не решена", "\nНе все клетки заполнены.", getCurrentStage());
                dialog.showDialog();
            }
            case INCORRECT -> {
                Dialog dialog = new Dialog(DualogType.ERROR, "Судоку решена неверно", "\nПоле заполнено с ошибками :(", getCurrentStage());
                dialog.showDialog();
            }
            default -> {
                Dialog dialog = new Dialog(DualogType.INFO, "Успех!", "\nСудоку решена верно!", getCurrentStage());
                dialog.showDialog();
            }
        }
    }
}

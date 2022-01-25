package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    // Текущая игра.
    private static Game game;

    // Текущая активная клетка.
    private static TextField currentTextField;

    @FXML
    private Button returnButton, helpButton, resetButton, solutionButton, saveButton, checkButton;

    public static void createGame(Sudoku sudoku) throws IOException {
        game = new Game(sudoku);
        Stage stage = game.generateGameStage();
        ColorThemeManager.setThemeToScene(stage.getScene(),
                GameController.class.getResource("/styles/dark_game_screen.css").toExternalForm(),
                GameController.class.getResource("/styles/white_game_screen.css").toExternalForm());
        game.resizeBoard(440);
        stage.show();
    }

    @FXML
    protected void onHelpButtonClick() {
        if (currentTextField != null) {
            game.showTip(currentTextField);
        }
    }

    @FXML
    public void onSolutionButtonClick() throws IOException {
        Dialog dialog = new Dialog("confirm", "Подтвердите действие", "Вы уверены, что хотите\nпосмотреть решение?",
                (Stage)helpButton.getScene().getWindow());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> {
            dialog.closeDialog();
            game.showSolution();
        });
    }

    @FXML
    public void onCheckButtonClick() {
        String res = game.checkAnswer();
        switch (res) {
            case "empty_cell" -> {
                Dialog dialog = new Dialog("info", "Судоку не решена", "\nНе все клетки заполнены.",
                        (Stage)helpButton.getScene().getWindow());
                dialog.showDialog();
            }
            case "incorrect" -> {
                Dialog dialog = new Dialog("info", "Судоку решена неверно", "\nПоле заполнено с ошибками :(",
                        (Stage)helpButton.getScene().getWindow());
                dialog.showDialog();
            }
            case "correct" -> {
                Dialog dialog = new Dialog("info", "Успех!", "\nСудоку решена верно!",
                        (Stage)helpButton.getScene().getWindow());
                dialog.showDialog();
            }
        }
    }

    @FXML
    protected void onSaveButtonClick() throws IOException {
        if(GameSaver.save(game.getSudoku(), (Stage)returnButton.getScene().getWindow())) {
            game.setSaved(true);
        }
    }

    @FXML
    protected void onReturnButtonClick() {
        String msg = (game.isSaved()) ? "Ваша игра успешно сохранена.\nВыйти в главное меню?" : "Ваша игра не сохранена.\nВы уверены, что хотите\nвыйти в главное меню?";
        Dialog dialog = new Dialog("confirm", "Подтвердите действие", msg,
                (Stage)helpButton.getScene().getWindow());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> {
            ((Stage)helpButton.getScene().getWindow()).close();
            StartModel.getStartModel().buildStartScreen().show();
        });
    }

    @FXML
    protected void onResetButtonClick() {
        Dialog dialog = new Dialog("confirm", "Подтвердите действие", "Ваше решение будет\nсброшено. Вы уверены?",
                 (Stage)helpButton.getScene().getWindow());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> {
            dialog.closeDialog();
            game.reset();
        });
    }

    public static void checkAndUpdateCurrTF(TextField currentTextField) throws IOException {
        TextField oldTextField = GameController.currentTextField;
        boolean isCorrect = true;
        if (GameController.currentTextField != null && !GameController.currentTextField.getText().equals("")) {
            try {
                int value = Integer.parseInt(GameController.currentTextField.getText());
                isCorrect = value > 0 && value <= game.getSudoku().getBoardSize();
            } catch (Exception e) {
                isCorrect = false;
            }
            if (isCorrect) {
                game.setSaved(false);
                game.updateCurrentPosition(GameController.currentTextField);
            } else {
                Dialog dialog = new Dialog("info", "Ошибка!", "\nНекорректное значение поля.",
                         (Stage)Stage.getWindows().get(0));
                dialog.showDialog();
                oldTextField.setFocusTraversable(true);
                oldTextField.selectAll();
                oldTextField.requestFocus();
                oldTextField.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (ColorThemeManager.isDarkTheme()) {
                            oldTextField.setBackground(new Background(new BackgroundFill(Color.valueOf("#525252"), null, null)));
                        } else {
                            oldTextField.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                        }
                        timer.cancel();
                    }
                }, 5*1000);
            }
        }
        if (isCorrect) {
            GameController.currentTextField = currentTextField;
        } else {
            GameController.currentTextField = oldTextField;
        }
    }

    public void onMinimizeButtonClick(ActionEvent actionEvent) {
        ToolBarManager.onMinimizeButtonClick((Stage)helpButton.getScene().getWindow());
    }

    public void onMaximizeButtonClick(ActionEvent actionEvent) {
        ToolBarManager.onMaximizeButtonClick((Stage)helpButton.getScene().getWindow());
        game.resizeBoard(((Stage)helpButton.getScene().getWindow()).getHeight() + 33);
        if (((Stage)helpButton.getScene().getWindow()).isMaximized()) {
            helpButton.setMinSize((helpButton.getScene().getWindow().getHeight() - 52 - 75) / 6, (helpButton.getScene().getWindow().getHeight() - 52 - 90) / 6);
            solutionButton.setMinSize((helpButton.getScene().getWindow().getHeight() - 52 - 75) / 6, (helpButton.getScene().getWindow().getHeight() - 52 - 90) / 6);
            checkButton.setMinSize((helpButton.getScene().getWindow().getHeight() - 52 - 75)  / 6, (helpButton.getScene().getWindow().getHeight() - 52 - 90) / 6);
            returnButton.setMinSize((helpButton.getScene().getWindow().getHeight() - 52 - 75) / 6, (helpButton.getScene().getWindow().getHeight() - 52 - 90) / 6);
            resetButton.setMinSize((helpButton.getScene().getWindow().getHeight() - 52 - 75) / 6, (helpButton.getScene().getWindow().getHeight() - 52 - 90) / 6);
            saveButton.setMinSize((helpButton.getScene().getWindow().getHeight() - 52 - 75) / 6, (helpButton.getScene().getWindow().getHeight() - 52 - 90) / 6);
        } else {
            helpButton.setMaxSize(50,50);
            solutionButton.setMaxSize(50,50);
            checkButton.setMaxSize(50,50);
            saveButton.setMaxSize(50,50);
            resetButton.setMaxSize(50,50);
            returnButton.setMaxSize(50,50);
            helpButton.setMinSize(50,50);
            solutionButton.setMinSize(50,50);
            checkButton.setMinSize(50,50);
            saveButton.setMinSize(50,50);
            resetButton.setMinSize(50,50);
            returnButton.setMinSize(50,50);
        }
    }

    public void onCloseButtonClick() throws IOException {
        onReturnButtonClick();
    }

    public void onMousePressed(MouseEvent me) {
        ToolBarManager.onMousePressed(me, (Stage)helpButton.getScene().getWindow());
    }

    public void onMouseMoved(MouseEvent me) {
        ToolBarManager.onMouseMoved(me, (Stage)helpButton.getScene().getWindow());
    }
}

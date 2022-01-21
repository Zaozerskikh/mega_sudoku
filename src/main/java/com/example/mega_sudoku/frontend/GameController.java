package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.Game;
import com.example.mega_sudoku.backend.GameSaver;
import com.example.mega_sudoku.backend.Sudoku;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private Button returnButton, helpButton, resetButton, solutionButton, saveButton;

    public static void createGame(Sudoku sudoku) throws IOException {
        game = new Game(sudoku);
        Stage stage = game.generateGameStage();
        stage.show();
        stage.setOnCloseRequest(dialogEvent -> {
            String msg = (game.isSaved()) ? "Ваша игра успешно сохранена.\nВыйти в главное меню?" : "Ваша игра не сохранена.\nВы уверены, что хотите выйти?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg);
            alert.setTitle("Подтвердите действие");
            alert.showAndWait().ifPresent(response -> {
                if (response.getText().equals("OK")) {
                    stage.close();
                    HelloController.returnStartScreen();
                } else {
                    dialogEvent.consume();
                }
            });
        });
    }

    @FXML
    protected void onHelpButtonClick(ActionEvent e) {
        if (currentTextField != null) {
            game.showTip(currentTextField);
        }
    }

    @FXML
    public void onSolutionButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите сдаться и посмотреть решение?");
        alert.setTitle("Подтвердите действие");
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                game.showSolution();
            }
        });
    }

    @FXML
    public void onCheckButtonClick(ActionEvent actionEvent) {
        String res = game.checkAnswer();
        switch (res) {
            case "empty_cell" -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Не все клетки заполнены.");
                alert.setTitle("Судоку не решена");
                alert.show();
            }
            case "incorrect" -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Поле заполнено с ошибками :(");
                alert.setTitle("Судоку решена неверно");
                alert.show();
            }
            case "correct" -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Судоку решена правильно!");
                alert.setTitle("Успех!");
                alert.show();
            }
        }
    }

    @FXML
    protected void onSaveButtonClick(ActionEvent e) {
        if(GameSaver.save(game.getSudoku(), (Stage)returnButton.getScene().getWindow())) {
            game.setSaved(true);
        }
    }

    @FXML
    protected void onReturnButtonClick(ActionEvent e) {
        String msg = (game.isSaved()) ? "Ваша игра успешно сохранена.\nВыйти в главное меню?" : "Ваша игра не сохранена.\nВы уверены, что хотите выйти?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg);
        alert.setTitle("Подтвердите действие");
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                ((Stage)returnButton.getScene().getWindow()).close();;
                HelloController.returnStartScreen();
            } else {
                e.consume();
            }
        });
    }

    @FXML
    protected void onResetButtonClick(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ваше решение будет сброшено. Вы уверены?");
        alert.setTitle("Подтвердите действие");
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                game.reset();
            } else {
                e.consume();
            }
        });
    }

    public static void checkAndUpdateCurrTF(TextField currentTextField) {
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
                Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректное значение поля!\nДолжно быть целое число от 0 до " + game.getSudoku().getBoardSize());
                alert.showAndWait();
                GameController.currentTextField.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                oldTextField.setFocusTraversable(true);
                oldTextField.selectAll();
                oldTextField.requestFocus();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        oldTextField.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                        timer.cancel();
                    }
                }, 2*1000);
            }
        }
        if (isCorrect) {
            GameController.currentTextField = currentTextField;
        } else {
            GameController.currentTextField = oldTextField;
        }
    }
}

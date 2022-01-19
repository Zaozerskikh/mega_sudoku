package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class GameController {
    private static Game game;
    private static TextField currentTextField;
    @FXML
    private Button returnButton, helpButton, resetButton, solutionButton, saveButton;

    public static void createGame(int size, int diffLevel) throws IOException {
        game = new Game(size, diffLevel);
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

    public void onSolutionButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите сдаться и посмотреть решение?");
        alert.setTitle("Подтвердите действие");
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                game.showSolution();
            }
        });
    }


    public void onCheckButtonClick(ActionEvent actionEvent) {
        String res = game.check();
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
        game.setSaved(true);
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
    protected void onResetButtonClick(ActionEvent e) throws IOException {
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

    public static void updateCurrentTextField(TextField currentTextField) {
        GameController.currentTextField = currentTextField;
    }
}

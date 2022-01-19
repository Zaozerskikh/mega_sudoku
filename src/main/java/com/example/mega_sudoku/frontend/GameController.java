package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class GameController {
    private static Game game;

    public static void createGame(int size, int diffLevel) throws IOException {
        game = new Game(size, diffLevel);
        Stage stage = game.generateGameStage();
        stage.show();
        stage.setOnCloseRequest(dialogEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите прервать игру и выйти?");
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

    }

    @FXML
    protected void onSaveButtonClick(ActionEvent e) {

    }

    @FXML
    protected void onReturnButtonClick(ActionEvent e) {

    }

    @FXML
    protected void onRulesButtonClick(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Правила судоку");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml_stages/game_rules_screen.fxml")));
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon.png"));
        stage.show();
    }
}

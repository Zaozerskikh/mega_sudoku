package com.example.mega_sudoku.frontend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloController {
    @FXML
    protected Button devInfoButton, helpButton, myGamesButton, newGameButton;

    @FXML
    protected void onNewGameButtonClick(ActionEvent e) throws Exception {
        showStage(getClass().getResource("/fxml_stages/new_game_settings_screen.fxml"), "Новая игра");
    }

    @FXML
    protected void onMyGamesButtonClick(ActionEvent e) throws IOException {
        showStage(getClass().getResource("/fxml_stages/my_games_screen.fxml"), "Мои сохраненные игры");
    }

    @FXML
    protected void onHelpButtonClick(ActionEvent e) throws Exception {
        showStage(getClass().getResource("/fxml_stages/help_screen.fxml"), "Помощь");
    }

    @FXML
    protected void onInfoButtonClick(ActionEvent e) throws IOException {
        showStage(getClass().getResource("/fxml_stages/dev_info_screen.fxml"), "Помощь");
    }

    private void showStage(URL resource, String stageName) throws IOException {
        ((Stage)helpButton.getScene().getWindow()).close();
        Stage stage = new Stage();
        stage.setTitle(stageName);
        stage.setResizable(false);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(resource)));
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon.png"));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> returnStartScreen());
    }

    public static void returnStartScreen() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(HelloController.class.getResource("/fxml_stages/hello_screen.fxml")))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Мега-Судоку");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.getIcons().add(new Image("/icon.png"));
        stage.show();
        stage.setOnCloseRequest(dialogEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите выйти из программы?");
            alert.setTitle("Подтвердите действие");
            alert.showAndWait().ifPresent(response -> {
                if (response.getText().equals("OK")) {
                    stage.close();
                } else {
                    dialogEvent.consume();
                }
            });
        });
    }
}
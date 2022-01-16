package com.example.mega_sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloController {
    @FXML
    protected Button devInfoButton, helpButton;

    @FXML
    protected void onInfoButtonClick(ActionEvent e) throws IOException {
        Stage.getWindows().stream().filter(Window::isShowing).toList().forEach(Window:: hide);
        Stage stage = new Stage();
        stage.setTitle("О разработчиках");
        stage.setResizable(false);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml_stages/dev_info_screen.fxml"))));
        showStage(stage, scene, getClass().getResource("/fxml_stages/hello_screen.fxml"));
    }

    @FXML
    protected void onHelpButtonClick(ActionEvent e) throws Exception {
        Stage.getWindows().stream().filter(Window::isShowing).toList().forEach(Window:: hide);
        Stage stage = new Stage();
        stage.setTitle("Помощь");
        stage.setResizable(false);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml_stages/help_screen.fxml"))));
        showStage(stage, scene, getClass().getResource("/fxml_stages/hello_screen.fxml"));
    }

    @FXML
    protected void onNewGameButtonClick(ActionEvent e) throws Exception {
        Stage.getWindows().stream().filter(Window::isShowing).toList().forEach(Window:: hide);
        Stage stage = new Stage();
        stage.setTitle("Новая игра");
        stage.setResizable(false);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml_stages/new_game_settings_screen.fxml"))));
        showStage(stage, scene, getClass().getResource("/fxml_stages/hello_screen.fxml"));
    }


    private void showStage(Stage stage, Scene scene, URL resource) {
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            Stage stage1 = new Stage();
            try {
                stage1.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(resource))));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            stage1.setTitle("Мега-Судоку");
            stage1.setResizable(false);
            stage1.show();
            stage1.setOnCloseRequest(dialogEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверенны, что хотите выйти из программы?");
                alert.setTitle("Подтвердите действие");
                alert.showAndWait().ifPresent(response -> {
                    if (response.getText().equals("OK")) {
                        stage1.close();
                    } else {
                        dialogEvent.consume();
                    }
                });
            });
        });
    }
}
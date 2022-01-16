package com.example.mega_sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml_stages/dev_info_screen.fxml"))));
        showHelpOrInfoStage(stage, scene, getClass().getResource("/fxml_stages/hello_screen.fxml"));
    }

    @FXML
    protected void onHelpButtonClick(ActionEvent e) throws Exception {
        Stage.getWindows().stream().filter(Window::isShowing).toList().forEach(Window:: hide);
        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml_stages/help_screen.fxml"))));
        showHelpOrInfoStage(stage, scene, getClass().getResource("/fxml_stages/hello_screen.fxml"));
    }


    private void showHelpOrInfoStage(Stage stage, Scene scene, URL resource) {
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            Stage stage1 = new Stage();
            try {
                stage1.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(resource))));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            stage1.setResizable(false);
            stage1.show();
        });
    }
}
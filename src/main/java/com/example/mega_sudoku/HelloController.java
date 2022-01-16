package com.example.mega_sudoku;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    protected Button devInfoButton, helpButton;

    @FXML
    protected void onInfoButtonClick() throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dev_info_screen.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onHelpButtonClick() throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help_screen.fxml"))));
        stage.setScene(scene);
        stage.show();
    }
}
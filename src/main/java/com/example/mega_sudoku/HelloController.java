package com.example.mega_sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    protected Button devInfoButton, helpButton;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        Stage stage = (Stage) devInfoButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("dev_info_screen.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onHelpButtonClick() throws IOException {
        Stage stage = (Stage) helpButton.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("help_screen.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
package com.example.mega_sudoku;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DevInfoController {
    @FXML
    protected Button returnToMainMenuButton;

    @FXML
    protected void onReturnButtonClick() throws IOException {
        Stage stage = (Stage) returnToMainMenuButton.getScene().getWindow();
        stage.setTitle("Мега-Судоку");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml_stages/hello_screen.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}

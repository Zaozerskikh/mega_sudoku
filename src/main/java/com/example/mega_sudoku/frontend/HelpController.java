package com.example.mega_sudoku.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {
    @FXML
    protected Button returnToMainMenuButton;

    @FXML
    protected void onReturnButtonClick() {
        ((Stage)returnToMainMenuButton.getScene().getWindow()).close();
        HelloController.returnStartScreen();
    }
}

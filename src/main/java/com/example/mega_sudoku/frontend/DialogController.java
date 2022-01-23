package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.ToolBarManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogController {
    @FXML
    private Button yesButton;

    @FXML
    protected void onCloseButtonClick(ActionEvent e) throws IOException {
        ToolBarManager.onCloseButtonClick(e, (Stage)yesButton.getScene().getWindow());
    }

    @FXML
    protected void onMouseMoved(MouseEvent e) throws IOException {
        ToolBarManager.onMouseMoved(e, (Stage)yesButton.getScene().getWindow());
    }

    @FXML
    protected void onMousePressed(MouseEvent e) throws IOException {
        ToolBarManager.onMousePressed(e, (Stage)yesButton.getScene().getWindow());
    }
}

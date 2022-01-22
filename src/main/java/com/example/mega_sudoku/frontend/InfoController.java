package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.ToolBarManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InfoController {
    @FXML
    private Button closeButton;

    public void onCloseButtonClick(ActionEvent actionEvent) {
        ToolBarManager.onCloseButtonClick(actionEvent, (Stage)closeButton.getScene().getWindow());
    }

    public void onMousePressed(MouseEvent me) {
        ToolBarManager.onMousePressed(me, (Stage)closeButton.getScene().getWindow());
    }

    public void onMouseMoved(MouseEvent me) {
        ToolBarManager.onMouseMoved(me, (Stage)closeButton.getScene().getWindow());
    }

    public void onMaximizeButtonClick(ActionEvent actionEvent) {
        ToolBarManager.onMaximizeButtonClick(actionEvent, (Stage)closeButton.getScene().getWindow());
    }

    public void onMinimizeButtonClick(ActionEvent actionEvent) {
        ToolBarManager.onMinimizeButtonClick(actionEvent, (Stage)closeButton.getScene().getWindow());
    }
}

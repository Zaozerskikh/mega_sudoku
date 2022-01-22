package com.example.mega_sudoku.backend;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ToolBarManager {
    private static double initialX;
    private static double initialY;

    public static void onCloseButtonClick(ActionEvent actionEvent, Stage stage) {
        stage.close();
    }

    public static void onMousePressed(MouseEvent me, Stage stage) {
        if (me.getButton() != MouseButton.MIDDLE) {
            initialX = me.getSceneX();
            initialY = me.getSceneY();
        } else {
            stage.getScene().getWindow().centerOnScreen();
            initialX = stage.getScene().getWindow().getX();
            initialY = stage.getScene().getWindow().getY();
        }
    }

    public static void onMouseMoved(MouseEvent me, Stage stage) {
        if (me.getButton()!=MouseButton.MIDDLE) {
            stage.getScene().getWindow().setX( me.getScreenX() - initialX );
            stage.getScene().getWindow().setY( me.getScreenY() - initialY);
        }
    }

    public static void onMaximizeButtonClick(ActionEvent actionEvent, Stage stage) {
        if (((Stage)stage.getScene().getWindow()).isMaximized()) {
            ((Stage)stage.getScene().getWindow()).setMaximized(false);
        } else {
            ((Stage)stage.getScene().getWindow()).setMaximized(true);
        }
    }

    public static void onMinimizeButtonClick(ActionEvent actionEvent, Stage stage) {
        ((Stage)stage.getScene().getWindow()).setIconified(true);
    }
}

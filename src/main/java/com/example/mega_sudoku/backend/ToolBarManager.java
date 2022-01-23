package com.example.mega_sudoku.backend;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Класс, отвечающий за обработку действий с кнопками toolbar'a сверху.
 */
public class ToolBarManager {
    // Текущее положение окна по Х.
    private static double initialX;

    // Текущее положение окна по У.
    private static double initialY;

    // Обработка закрытия окна.
    public static void onCloseButtonClick(ActionEvent actionEvent, Stage stage) {
        stage.close();
    }

    /**
     * Обработка нажатия мыши (необходимо для перемещения окна).
     * @param me событие.
     * @param stage окно.
     */
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


    /**
     * Обработка перемещения мыши (необходимо для перемещения окна).
     * @param me событие.
     * @param stage окно.
     */
    public static void onMouseMoved(MouseEvent me, Stage stage) {
        if (me.getButton()!=MouseButton.MIDDLE) {
            stage.getScene().getWindow().setX( me.getScreenX() - initialX );
            stage.getScene().getWindow().setY( me.getScreenY() - initialY);
        }
    }


    /**
     * Обработка максимизации окна.
     * @param actionEvent событие.
     * @param stage окно.
     */
    public static void onMaximizeButtonClick(ActionEvent actionEvent, Stage stage) {
        if (((Stage)stage.getScene().getWindow()).isMaximized()) {
            ((Stage)stage.getScene().getWindow()).setMaximized(false);
        } else {
            ((Stage)stage.getScene().getWindow()).setMaximized(true);
        }
    }


    /**
     * Обработка минимизации окна.
     * @param actionEvent событие.
     * @param stage окно.
     */
    public static void onMinimizeButtonClick(ActionEvent actionEvent, Stage stage) {
        ((Stage)stage.getScene().getWindow()).setIconified(true);
    }
}

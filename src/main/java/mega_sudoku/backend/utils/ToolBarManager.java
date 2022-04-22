package mega_sudoku.backend.utils;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Класс, отвечающий за обработку действий с кнопками на панели сверху, и за перемещение окна мышкой.
 */
public class ToolBarManager {
    // Текущее положение окна по Х.
    private static double initialX;

    // Текущее положение окна по У.
    private static double initialY;

    /**
     * Обработка закрытия окна.
     * @param stage окно, которое необходимо закрыть.
     */
    public static void onCloseButtonClick(Stage stage) {
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
        if (me.getButton() != MouseButton.MIDDLE && !stage.isFullScreen()) {
            stage.getScene().getWindow().setX( me.getScreenX() - initialX );
            stage.getScene().getWindow().setY( me.getScreenY() - initialY);
        }
    }

    /**
     * Обработка максимизации окна.
     * @param stage окно.
     */
    public static void onMaximizeButtonClick(Stage stage) {
        stage.setFullScreen(!stage.isFullScreen());
    }


    /**
     * Обработка минимизации окна.
     * @param stage окно.
     */
    public static void onMinimizeButtonClick(Stage stage) {
        ((Stage)stage.getScene().getWindow()).setIconified(true);
    }
}

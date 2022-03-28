package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.ToolBarManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Контроллер диалогового окна.
 */
public class DialogController {
    // Кнопка "Да"
    @FXML
    private Button yesButton;

    /**
     * Получение текущего окна с диалогом.
     * @return текущее окно с диалогом.
     */
    private Stage getCurrentStage() {
        return (Stage)yesButton.getScene().getWindow();
    }

    /**
     * Обработка нажатия на кнопку "Закрыть окно" в правом верхнем углу окна.
     */
    @FXML
    protected void onCloseButtonClick() {
        ToolBarManager.onCloseButtonClick(getCurrentStage());
    }

    /**
     * Обработка перемещения мыши (необходимо для работы перетаскивания окна).
     * @param e Mouse event.
     */
    @FXML
    protected void onMouseMoved(MouseEvent e) {
        ToolBarManager.onMouseMoved(e, getCurrentStage());
    }

    /**
     * Обработка нажатия мыши (необходимо для работы перетаскивания окна).
     * @param e Mouse event.
     */
    @FXML
    protected void onMousePressed(MouseEvent e) {
        ToolBarManager.onMousePressed(e, getCurrentStage());
    }

    // TODO: Проверить свертываемость окна на MAC OS.
}

package mega_sudoku.frontend.controllers;

import mega_sudoku.backend.utils.ToolBarManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Контроллер для окон "Помощь" и "Информация о разработчиках".
 */
public class InfoController {
    // Кнопка закрытия окна.
    @FXML
    private Button closeButton;

    // Получение текущего окна.
    private Stage getCurrentStage() {
        return (Stage)closeButton.getScene().getWindow();
    }

    /**
     * Обработка нажатия на кнопку "Закрыть окно" в правом верхнем углу окна.
     */
    public void onCloseButtonClick() {
        ToolBarManager.onCloseButtonClick(getCurrentStage());
    }

    /**
     * Обработка нажатия мыши (необходимо для работы перетаскивания окна).
     * @param me Mouse event.
     */
    public void onMousePressed(MouseEvent me) {
        ToolBarManager.onMousePressed(me, getCurrentStage());
    }

    /**
     * Обработка перемещения мыши (необходимо для работы перетаскивания окна).
     * @param me Mouse event.
     */
    public void onMouseMoved(MouseEvent me) {
        ToolBarManager.onMouseMoved(me, getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Развернуть на полный экран" в правом верхнем углу окна.
     */
    public void onMaximizeButtonClick() {
        ToolBarManager.onMaximizeButtonClick(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Свернуть окно" в правом верхнем углу окна.
     */
    public void onMinimizeButtonClick() {
        ToolBarManager.onMinimizeButtonClick(getCurrentStage());
    }
}

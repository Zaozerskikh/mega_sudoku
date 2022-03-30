package mega_sudoku.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mega_sudoku.backend.utils.ToolBarManager;

public class ProgressBarController {
    @FXML
    protected Label progressLabel;

    private Stage getCurrentStage() {
        return (Stage) progressLabel.getScene().getWindow();
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
}

package mega_sudoku.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mega_sudoku.backend.game.GameStageBuilder;
import mega_sudoku.backend.models.StartModel;
import mega_sudoku.backend.sudoku.SudokuBuilder;
import mega_sudoku.backend.utils.ToolBarManager;

/**
 * Контроллер для окна настроек.
 */
public class NewGameSettingsController {
    // Размер доски, выбранный пользователем (по умолчанию - 12).
    private int boardSize = 16;

    // Уровень сложности, выбранный пользователем (по умолчанию - 1).
    private int difficultyLevel = 1;

    // Кнопка начала новой игры.
    @FXML
    protected Button startGameButton;

    // Чекбокс для размера поля 12.
    @FXML
    protected CheckBox box25;

    // Чекбокс для размера поля 16.
    @FXML
    protected CheckBox box16;

    // Слайдер изменения уровня сложности.
    @FXML
    protected Slider diffSlider;

    // Надпись легкого уровня сложности.
    @FXML
    protected Label easy;

    // Надпись среднего уровня сложности.
    @FXML
    protected Label medium;

    // Надпись сложного уровня сложности.
    @FXML
    protected Label hard;

    private Stage getCurrentStage() {
        return (Stage)startGameButton.getScene().getWindow();
    }

    /**
     * Обработка выбора размера поля 16.
     */
    @FXML
    protected void selected16() {
        if (box25.isSelected()) {
            box25.setSelected(false);
            box16.setSelected(true);
            boardSize = 16;
        } else {
            box25.setSelected(true);
            box16.setSelected(false);
            boardSize = 25;
        }
    }

    /**
     * Обработка выбора размера поля 25.
     */
    @FXML
    protected void selected25() {
        if (box16.isSelected()) {
            box16.setSelected(false);
            box25.setSelected(true);
            boardSize = 25;
        } else {
            box16.setSelected(true);
            box25.setSelected(false);
            boardSize = 16;
        }
    }

    /**
     * Обработка изменения положения слайдера сложности генерируемой судоку
     */
    @FXML
    protected void sliderChange() {
        difficultyLevel = (int) diffSlider.getValue();
        switch (difficultyLevel) {
            case 1 -> {
                easy.setFont(new Font(19));
                easy.setLayoutY(252);
                easy.setOpacity(1);
                medium.setFont(new Font(16));
                medium.setLayoutY(255);
                medium.setOpacity(0.6);
                hard.setFont(new Font(16));
                hard.setLayoutY(255);
                hard.setOpacity(0.6);
            }
            case 2 -> {
                medium.setFont(new Font(19));
                medium.setLayoutY(252);
                medium.setOpacity(1);
                easy.setFont(new Font(16));
                easy.setLayoutY(255);
                easy.setOpacity(0.6);
                hard.setOpacity(0.6);
                hard.setFont(new Font(16));
                hard.setLayoutY(255);
                hard.setOpacity(0.6);
            }
            case 3 -> {
                hard.setFont(new Font(19));
                hard.setLayoutY(252);
                hard.setOpacity(1);
                medium.setFont(new Font(16));
                medium.setLayoutY(255);
                medium.setOpacity(0.6);
                easy.setFont(new Font(16));
                easy.setLayoutY(255);
                easy.setOpacity(0.6);
            }
        }
    }

    /**
     * Обработка нажатия на кнопку генерации судоку.
     */
    @FXML
    protected void onNewGameButtonClick() {
        getCurrentStage().close();
        SudokuBuilder.getSudokuBuilder().generateSudoku(boardSize, difficultyLevel);
        GameStageBuilder.generateAndShowGameStage(SudokuBuilder.getSudokuBuilder().getGeneratedSudoku());
    }

    /**
     * Обработка нажатия на кнопку "Закрыть окно" в правом верхнем углу окна.
     */
    @FXML
    protected void onCloseButtonClick() {
        ToolBarManager.onCloseButtonClick(getCurrentStage());
        StartModel.getStartModel().buildStartScreen().show();
    }

    /**
     * Обработка нажатия на кнопку "Свернуть окно" в правом верхнем углу окна.
     */
    @FXML
    protected void onMinimizeButtonClick() {
        ToolBarManager.onMinimizeButtonClick(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Развернуть на весь экран" в правом верхнем углу окна.
     */
    @FXML
    protected void onMaximizeButtonClick() {
        ToolBarManager.onMaximizeButtonClick(getCurrentStage());
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

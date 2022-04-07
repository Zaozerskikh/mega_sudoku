package mega_sudoku.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mega_sudoku.backend.models.NewGameSettingsModel;
import mega_sudoku.backend.sudoku.DifficultyLevel;
import mega_sudoku.backend.utils.CommonStageBuilder;
import mega_sudoku.backend.utils.ToolBarManager;

/**
 * Контроллер для окна настроек.
 */
public class NewGameSettingsController {
    public NewGameSettingsController() {
        model = new NewGameSettingsModel();
    }

    // Модель окна настроек новой игры.
    private final NewGameSettingsModel model;

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
    protected Label easyLabel;

    // Надпись среднего уровня сложности.
    @FXML
    protected Label mediumLabel;

    // Надпись сложного уровня сложности.
    @FXML
    protected Label hardLabel;

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
            model.setBoardSize(16);
        } else {
            box25.setSelected(true);
            box16.setSelected(false);
            model.setBoardSize(25);
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
            model.setBoardSize(25);
        } else {
            box16.setSelected(true);
            box25.setSelected(false);
            model.setBoardSize(16);
        }
    }

    /**
     * Обработка изменения положения слайдера сложности генерируемой судоку
     */
    @FXML
    protected void sliderChange() {
        switch ((int)diffSlider.getValue()) {
            case 1 -> model.setDifficultyLevel(DifficultyLevel.EASY);
            case 2 -> model.setDifficultyLevel(DifficultyLevel.MEDIUM);
            case 3 -> model.setDifficultyLevel(DifficultyLevel.HARD);
        }

        switch (model.getDifficultyLevel()) {
            case EASY -> changeFontSize(easyLabel, hardLabel);
            case MEDIUM -> {
                mediumLabel.setFont(new Font(19));
                mediumLabel.setLayoutY(252);
                mediumLabel.setOpacity(1);
                easyLabel.setFont(new Font(16));
                easyLabel.setLayoutY(255);
                easyLabel.setOpacity(0.6);
                hardLabel.setOpacity(0.6);
                hardLabel.setFont(new Font(16));
                hardLabel.setLayoutY(255);
                hardLabel.setOpacity(0.6);
            }
            case HARD -> changeFontSize(hardLabel, easyLabel);
        }
    }

    // Изменение размера шрифта при перетаскивании ползунка слайдера.
    private void changeFontSize(Label hard, Label easy) {
        hard.setFont(new Font(19));
        hard.setLayoutY(252);
        hard.setOpacity(1);
        mediumLabel.setFont(new Font(16));
        mediumLabel.setLayoutY(255);
        mediumLabel.setOpacity(0.6);
        easy.setFont(new Font(16));
        easy.setLayoutY(255);
        easy.setOpacity(0.6);
    }

    /**
     * Обработка нажатия на кнопку генерации судоку.
     */
    @FXML
    protected void onNewGameButtonClick() {
        model.newGameStartProcess();
        getCurrentStage().close();
    }

    /**
     * Обработка нажатия на кнопку "Закрыть окно" в правом верхнем углу окна.
     */
    @FXML
    protected void onCloseButtonClick() {
        CommonStageBuilder.buildStartStage().show();
        ToolBarManager.onCloseButtonClick(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Свернуть окно" в правом верхнем углу окна.
     */
    @FXML
    protected void onMinimizeButtonClick() {
        ToolBarManager.onMinimizeButtonClick(getCurrentStage());
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

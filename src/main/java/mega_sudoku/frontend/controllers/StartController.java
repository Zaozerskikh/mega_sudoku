package mega_sudoku.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mega_sudoku.backend.game.GameLoader;
import mega_sudoku.backend.utils.*;

import java.util.Objects;

/**
 * Контроллер стартового экрана приложения.
 */
public class StartController {
    // Кнопка начала новой игры.
    @FXML
    protected Button newGameButton;

    // Кнопка загрузки сохраненной игры.
    @FXML
    protected Button loadGameButton;

    // Кнопка помощи игроку.
    @FXML
    protected Button helpButton;

    // Кпонка информации о разработчиках.
    @FXML
    protected Button devInfoButton;

    // Кнопка включения и выключения темной темы.
    @FXML
    protected Button darkThemeButton;

    // Кнопка закрытия окна.
    @FXML
    protected Button closeButton;

    /**
     * Получение текущего окна как объект Stage.
     * @return stage.
     */
    private Stage getCurrentStage() {
        return (Stage)helpButton.getScene().getWindow();
    }


    /**
     * Обработка нажатия на кнопку новой игры.
     */
    @FXML
    protected void onNewGameButtonClick() {
        getCurrentStage().close();
        CommonStageBuilder.buildNewGameSettingsStage(getCurrentStage()).show();
    }

    /**
     * Обработка нажатия на кнопку загрузки сохраненной игры из файла.
     */
    @FXML
    protected void onLoadSavedGameButtonClick() {
        GameLoader.showLoadDialog(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Помощь".
     */
    @FXML
    protected void onHelpButtonClick() {
        CommonStageBuilder.buildHelpStage(getCurrentStage()).show();
    }

    /**
     * Обработка нажатия на кнопку "Информация о разработчиках".
     */
    @FXML
    protected void onInfoButtonClick() {
        CommonStageBuilder.buildDevInfoStage(getCurrentStage()).show();
    }

    /**
     * Обработка нажатия на кнопку "Тёмной темы".
     */
    @FXML
    public void onDarkThemeButtonClick() {
        ColorThemeManager.switchTheme();
        ColorThemeManager.setThemeToScene(getCurrentStage().getScene(),
                Objects.requireNonNull(this.getClass().getResource("/styles/dark_start_screen.css")).toExternalForm(),
                Objects.requireNonNull(this.getClass().getResource("/styles/white_start_screen.css")).toExternalForm());
    }

    /**
     * Обработка нажатия на кнопку "Свернуть окно" в правом верхнем углу окна.
     */
    @FXML
    public void onMinimizeButtonClick() {
        ToolBarManager.onMinimizeButtonClick(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Закрыть окно" в правом верхнем углу окна.
     */
    @FXML
    public void onCloseButtonClick() {
        Dialog dialog = new Dialog(DualogType.CONFIRM, "Подтвердите действие", "\nВыйти из программы?", getCurrentStage());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> getCurrentStage().close());
    }

    /**
     * Обработка нажатия мыши (необходимо для работы перетаскивания окна).
     * @param me Mouse event.
     */
    @FXML
    public void onMousePressed(MouseEvent me) {
        ToolBarManager.onMousePressed(me, getCurrentStage());
    }

    /**
     * Обработка перемещения мыши (необходимо для работы перетаскивания окна).
     * @param me Mouse event.
     */
    @FXML
    public void onMouseMoved(MouseEvent me) {
        ToolBarManager.onMouseMoved(me, getCurrentStage());
    }
}
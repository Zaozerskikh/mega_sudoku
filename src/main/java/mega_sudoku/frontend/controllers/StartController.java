package mega_sudoku.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mega_sudoku.backend.game.GameLoader;
import mega_sudoku.backend.models.StartModel;
import mega_sudoku.backend.utils.ColorThemeManager;
import mega_sudoku.backend.utils.Dialog;
import mega_sudoku.backend.utils.ToolBarManager;

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

    // Модель начального экрана приложения с бизнес логикой.
    private final StartModel startModel = StartModel.getStartModel();

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
        startModel.buildChildStage(getCurrentStage(), getClass().getResource("/fxml_stages/new_game_settings_screen.fxml"), "Новая игра",
                Objects.requireNonNull(this.getClass().getResource("/styles/dark_settings_screen.css")).toExternalForm(),
                Objects.requireNonNull(this.getClass().getResource("/styles/white_settings_screen.css")).toExternalForm()).show();
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
        startModel.buildChildStage(getCurrentStage(), getClass().getResource("/fxml_stages/help_screen.fxml"),"Помощь",
                Objects.requireNonNull(this.getClass().getResource("/styles/dark_info_screen.css")).toExternalForm(),
                Objects.requireNonNull(this.getClass().getResource("/styles/white_info_screen.css")).toExternalForm()).show();
    }

    /**
     * Обработка нажатия на кнопку "Информация о разработчиках".
     */
    @FXML
    protected void onInfoButtonClick() {
        startModel.buildChildStage(getCurrentStage(), getClass().getResource("/fxml_stages/dev_info_screen.fxml"), "О разработчиках",
                Objects.requireNonNull(this.getClass().getResource("/styles/dark_info_screen.css")).toExternalForm(),
                Objects.requireNonNull(this.getClass().getResource("/styles/white_info_screen.css")).toExternalForm()).show();
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
     * Обработка нажатия на кнопку "Развернуть на полный экран" в правом верхнем углу окна.
     */
    @FXML
    public void onMaximizeButtonClick() {
        ToolBarManager.onMaximizeButtonClick(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Закрыть окно" в правом верхнем углу окна.
     */
    @FXML
    public void onCloseButtonClick() {
        Dialog dialog = new Dialog("confirm", "Подтвердите действие", "\nВыйти из программы?", getCurrentStage());
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
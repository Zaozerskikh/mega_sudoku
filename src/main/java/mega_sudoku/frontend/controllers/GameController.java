package mega_sudoku.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mega_sudoku.backend.utils.CommonStageBuilder;
import mega_sudoku.backend.utils.Dialog;
import mega_sudoku.backend.utils.DualogType;
import mega_sudoku.backend.utils.ToolBarManager;
import mega_sudoku.frontend.views.GameView;

/**
 * Контроллер для окна с игрой.
 */
public class GameController extends GameView {

    // Кнопка проверки решения игрока.
    @FXML
    protected Button checkButton;

    // Кнопка получения игроком подсказки.
    @FXML
    protected Button helpButton;

    // Кнопка показа пользователю решения.
    @FXML
    protected Button solutionButton;

    // Кнопка сохранения текущего состояни головоломки в файл.
    @FXML
    protected Button saveButton;

    // Кнопка возврата на главный экран приложения.
    @FXML
    protected Button returnButton;

    // Кнопка сброса решения пользователя и возврата головоломки к состоянию после генерации.
    @FXML
    protected Button resetButton;

    /**
     * Конструктор класса. (на все клетки игровой доски вешается событие на их изменение).
     */
    public GameController() {
        gameBoard.getChildren().forEach(x -> x.setOnMousePressed(mouseEvent -> selectedCellChanged((TextField) mouseEvent.getSource())));
    }

    /**
     * Обработка изменения активной клетки игровой таблицы
     * @param newTextField новая активная клетка.
     */
    public void selectedCellChanged(TextField newTextField) {
        gameModel.selectedCellChanged(newTextField);
    }

    /**
     * Обработка нажатия на кнопку подсказки.
     */
    @FXML
    protected void onHelpButtonClick() {
        gameModel.showTip();
    }

    /**
     * Обработка нажатия на кнопку "Показать решение".
     */
    @FXML
    public void onSolutionButtonClick() {
        Dialog dialog = new Dialog(DualogType.CONFIRM, "Подтвердите действие", "Вы уверены, что хотите\nпосмотреть решение?", getCurrentStage());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> {
            dialog.closeDialog();
            gameModel.showSolution();
        });
    }

    /**
     * Обработка нажатия на кнопку проверки пользовательского решения.
     */
    @FXML
    public void onCheckButtonClick() {
        gameModel.checkAnswer();
    }

    /**
     * Обработка нажатия на кнопку "Сохранить игру".
     */
    @FXML
    protected void onSaveButtonClick() {
        gameModel.saveGame(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Вернуться в меню".
     */
    @FXML
    protected void onReturnButtonClick() {
        String msg = (gameModel.isSaved()) ? "Ваша игра успешно сохранена.\nВыйти в главное меню?" : "Ваша игра не сохранена.\nВы уверены, что хотите\nвыйти в главное меню?";
        Dialog dialog = new Dialog(DualogType.CONFIRM, "Подтвердите действие", msg, getCurrentStage());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> {
            CommonStageBuilder.buildStartStage().show();
            getCurrentStage().close();
        });
    }

    /**
     * Обработка нажатия на кнопку сброса решения.
     */
    @FXML
    protected void onResetButtonClick() {
        Dialog dialog = new Dialog(DualogType.CONFIRM, "Подтвердите действие", "Ваше решение будет\nсброшено. Вы уверены?",
                 (Stage)helpButton.getScene().getWindow());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> {
            dialog.closeDialog();
            gameModel.resetPosition();
        });
    }

    /**
     * Обработка нажатия на кнопку "Свернуть окно" в правом верхнем углу окна.
     */
    public void onMinimizeButtonClick() {
        ToolBarManager.onMinimizeButtonClick(getCurrentStage());
    }

    /**
     * Обработка нажатия на кнопку "Развернуть на полный экран" в правом верхнем углу окна.
     */
    public void onMaximizeButtonClick() {
        ToolBarManager.onMaximizeButtonClick(getCurrentStage());
        var controls = new Button[] {checkButton, helpButton, solutionButton, saveButton, returnButton, resetButton};
        gameModel.resizeStage(getCurrentStage().getHeight() + 33, controls);
    }

    /**
     * Обработка нажатия на кнопку "Закрыть окно" в правом верхнем углу окна.
     */
    public void onCloseButtonClick() {
        onReturnButtonClick();
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
}

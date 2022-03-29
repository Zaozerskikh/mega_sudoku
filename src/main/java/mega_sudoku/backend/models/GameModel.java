package mega_sudoku.backend.models;

import mega_sudoku.backend.game.GameSaver;
import mega_sudoku.backend.sudoku.Sudoku;
import mega_sudoku.backend.sudoku.SudokuBuilder;
import mega_sudoku.frontend.views.GameView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Класс модели игры, реализующий бизнес логику игры.
 */
public class GameModel {
    // Представление окна игры.
    private final GameView view;

    // Судоку.
    private final Sudoku sudoku;

    // Текущее выбранное поле.
    private TextField selectedField;

    // Сохранено ли текущее состояние игры.
    private boolean saved;

    /**
     * Проверка сохранено ли текущее состояние игры.
     * @return результат проверки.
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Установка сохранения.
     * @param saved текущее состояние (сохранено или нет).
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * Конструктор модели.
     * @param view объект-представление для данной модели.
     */
    public GameModel(GameView view) {
        this.view = view;
        this.sudoku = SudokuBuilder.getSudokuBuilder().getGeneratedSudoku();
    }

    /**
     * Обработка запроса на проверку решения пользователя.
     */
    public void checkAnswer() {
        for (int i = 0; i < sudoku.getBoardSize(); i++) {
            for (int j = 0; j < sudoku.getBoardSize(); j++) {
                if (sudoku.getCurrentPosition()[i][j] == -1) {
                    view.displayCheckAnswerResult("empty_cell");
                    return;
                }
            }
        }
        for (int i = 0; i < sudoku.getBoardSize(); i++) {
            for (int j = 0; j < sudoku.getBoardSize(); j++) {
                if (sudoku.getCurrentPosition()[i][j] != sudoku.getSolution()[i][j]) {
                    view.displayCheckAnswerResult("incorrect");
                    return;
                }
            }
        }
        view.displayCheckAnswerResult("correct");
    }

    /**
     * Обработка запроса на получение подсказки.
     */
    public void showTip() {
        if (selectedField != null) {
            sudoku.updateCurrentPosition(sudoku.getSolution()[GridPane.getColumnIndex(selectedField)][GridPane.getRowIndex(selectedField)],
                    GridPane.getColumnIndex(selectedField), GridPane.getRowIndex(selectedField));
            if (selectedField.editableProperty().getValue()) {
                view.showTip(selectedField, sudoku.getSolution()[GridPane.getColumnIndex(selectedField)][GridPane.getRowIndex(selectedField)]);
            }
        }
    }

    /**
     * Обработка запроса на показ полного решения.
     */
    public void showSolution() {
        sudoku.setCurrentPosition(sudoku.getSolution());
        view.showSolution(sudoku.getSolution());
    }

    /**
     * Обработка запроса на сохранение игры.
     * @param parentStage родительское окно, из которого был послан запрос.
     */
    public void saveGame(Stage parentStage) {
        try {
            if(GameSaver.save(sudoku, parentStage)) {
                setSaved(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обработка запроса на сброс головоломки к исходному состоянию.
     */
    public void resetPosition() {
        sudoku.setCurrentPosition(sudoku.getProblem());
        view.resetPosition(sudoku.getProblem());
    }

    /**
     * Обработка ресайза игрового окна.
     * @param stageHeight новый размер игрового окна.
     * @param controls все кнопки, находящиеся в этом окне.
     */
    public void resizeStage(double stageHeight, Button[] controls) {
        view.resizeStage(stageHeight, sudoku.getBoardSize(), controls);
    }

    /**
     * Обработка изменения выбранной клетки.
     * @param newTextField новая выбранная клетка.
     */
    public void selectedCellChanged(TextField newTextField) {
        boolean isCorrect = true;
        if (selectedField != null && !selectedField.getText().equals("")) {
            try {
                int value = Integer.parseInt(selectedField.getText());
                isCorrect = value > 0 && value <= sudoku.getBoardSize();
            } catch (Exception e) {
                isCorrect = false;
            }
            if (isCorrect) {
                setSaved(false);
                sudoku.updateCurrentPosition(Integer.parseInt(selectedField.getText()),
                        GridPane.getColumnIndex(selectedField), GridPane.getRowIndex(selectedField));
            } else {
                view.showErrorByIncorrectCellValue(selectedField);
            }
        }
        if (isCorrect) {
            this.selectedField = newTextField;
        }
    }
}

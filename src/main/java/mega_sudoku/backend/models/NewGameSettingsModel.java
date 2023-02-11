package mega_sudoku.backend.models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import mega_sudoku.backend.game.GameStageBuilder;
import mega_sudoku.backend.sudoku.DifficultyLevel;
import mega_sudoku.backend.sudoku.SudokuBuilder;
import mega_sudoku.backend.utils.CommonStageBuilder;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Класс модели настроек новой игры.
 */
public class NewGameSettingsModel {
    public NewGameSettingsModel() {
        this.boardSize = 16;
        this.difficultyLevel = DifficultyLevel.EASY;
    }

    // Уровень сложности.
    private DifficultyLevel difficultyLevel;

    // Размер доски.
    private int boardSize;

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * Обработка нажатия кнопки начала новой игры:
     * Запуск progress bar, запуск генерации судоку.
     */
    public void newGameStartProcess() {
        // Генерация progress bar.
        var progressStage = CommonStageBuilder.buildProgressBarStage();
        Label progressLabel = (Label)progressStage.getScene().getRoot().getChildrenUnmodifiable().get(1);
        progressStage.show();

        // Обновление progress bar.
        var builder = SudokuBuilder.getSudokuBuilder();
        DecimalFormat df = new DecimalFormat("00.00");
        df.setRoundingMode(RoundingMode.DOWN);
        var progressLine = new Timeline(new KeyFrame(new javafx.util.Duration(100), event -> {
            if (builder.getEmptyCellsCount() / (float) builder.getTotalEmptyCells() * 100 < 100) {
                progressLabel.setText("   " + df.format(builder.getEmptyCellsCount() / (float) builder.getTotalEmptyCells() * 100) + "%   ");
            } else {
                progressLabel.setText("Отрисовка...");
            }
        }));
        progressLine.setCycleCount(Timeline.INDEFINITE);
        progressLine.play();

        // Генерация судоку и последующая отрисовка окна с игрой.
        new Thread(() -> {
            SudokuBuilder.getSudokuBuilder().generateSudoku(boardSize, difficultyLevel);
            Platform.runLater(() -> {
                progressLine.stop();
                GameStageBuilder.generateAndShowGameStage(builder.getGeneratedSudoku());
                progressStage.close();
            });
        }).start();
    }
}

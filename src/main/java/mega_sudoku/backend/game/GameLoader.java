package mega_sudoku.backend.game;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mega_sudoku.backend.sudoku.Sudoku;
import mega_sudoku.backend.sudoku.SudokuBuilder;
import mega_sudoku.backend.utils.Dialog;
import mega_sudoku.backend.utils.DialogType;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Класс, отвечающий за загрузку сохранённой игры.
 */
public class GameLoader {
    /**
     * Загрузка судоку из файла.
     * @param file файл с сохранённой игрой.
     * @return загруженная судоку.
     */
    private static Sudoku load(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            var sudoku = (Sudoku)objectInputStream.readObject();
            SudokuBuilder.getSudokuBuilder().setGeneratedSudoku(sudoku);
            return sudoku;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Метод, показывающий диалоговое окно загрузки сохраненной игры из файла.
     * @param parentStage родительское окно, из которого метод вызывается.
     */
    public static void showLoadDialog(Stage parentStage) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku files (*.sudoku)", "*.sudoku"));
        var file = fc.showOpenDialog(parentStage);
        if (file != null) {
            Sudoku sudoku = GameLoader.load(file);
            if (sudoku != null) {
                parentStage.close();
                GameStageBuilder.generateAndShowGameStage(sudoku);
            } else {
                Dialog error = new Dialog(DialogType.ERROR, "Ошибка", "\nНе удалось загрузить игру.\nПопробуйте еще раз.", parentStage);
                error.showDialog();
            }
        }
    }
}

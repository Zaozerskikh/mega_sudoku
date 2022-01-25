package com.example.mega_sudoku.backend;

import com.example.mega_sudoku.frontend.GameController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

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
            return (Sudoku)objectInputStream.readObject();
        } catch (Exception ex) {
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
                try {
                    GameController.createGame(sudoku);
                } catch (IOException e) {
                    StartModel.getStartModel().buildStartScreen();
                    Dialog error = new Dialog("error", "Ошибка", "\nНе удалось загрузить игру.\nПопробуйте еще раз.", parentStage);
                    error.showDialog();
                }
            } else {
                Dialog error = new Dialog("error", "Ошибка", "\nНе удалось загрузить игру.\nПопробуйте еще раз.", parentStage);
                error.showDialog();
            }
        }
    }
}

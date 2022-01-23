package com.example.mega_sudoku.backend;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Класс, отвечающий за сохранение игры в файл.
 */
public class GameSaver {

    /**
     * Сохранение игры в файл.
     * @param sudoku судоку для сохранения.
     * @param parentStage окно игры (параметр FileChooser)
     * @return удалось ли корректно сохранить игру.
     */
    public static boolean save(Sudoku sudoku, Stage parentStage) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName("MyGame");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku files (*.sudoku)", "*.sudoku"));
        var file = fc.showSaveDialog(parentStage);
        try {
            if (file != null) {
                if (!file.getPath().substring(file.getPath().indexOf('.') +1).equals("sudoku")) {
                    throw new Exception("incorrect_ext");
                }
                FileOutputStream outputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(sudoku);
                objectOutputStream.close();
                Dialog dialog = new Dialog("info", "Успех!", "\nВаша игра успешно сохранена!",
                        (Stage)Stage.getWindows().get(0));
                dialog.showDialog();
                return true;
            }
        } catch (Exception ex) {
            String exMsg = (ex.getMessage().equals("incorrect_ext") ? "Не удалось сохранить\nневерное расширение файла" : "К сожалению,\nигру сохранить не удалось.\nПопробуйте еще раз.");
            Dialog dialog = new Dialog("info", "Успех!", exMsg,
                    (Stage)Stage.getWindows().get(0));
            dialog.showDialog();
            return false;
        }
        return false;
    }
}

package com.example.mega_sudoku.backend;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GameSaver {
    public static boolean save(Sudoku sudoku, Stage parentStage) {
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ваша игра успешно сохранена.");
                alert.showAndWait();
                return true;
            }
        } catch (Exception ex) {
            String exMsg = (ex.getMessage().equals("incorrect_ext") ? "Не удалось сохранить - неверное расширение файла" : "К сожалению, игру сохранить не удалось. Попробуйте еще раз.");
            Alert alert = new Alert(Alert.AlertType.ERROR, exMsg);
            alert.showAndWait();
            return false;
        }
        return false;
    }
}

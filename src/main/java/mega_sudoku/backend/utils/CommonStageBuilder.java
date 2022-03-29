package mega_sudoku.backend.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CommonStageBuilder {
    /**
     * Генерация дочернего окна.
     * @param parentStage родительское окно.
     * @param resource ссылка на fxml дочернего окна.
     * @param stageName название дочернего окна.
     * @param darkPath путь к css файлу с темной темой дочернего окна.
     * @param whitePath путь к css файлу со светлой темой дочернего окна.
     * @return сгенерированное дочернее окно.
     */
    public static Stage buildStage(Stage parentStage, URL resource, String stageName, String darkPath, String whitePath) {
        Stage stage = new Stage();
        stage.setTitle(stageName);
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(resource)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ColorThemeManager.setThemeToScene(scene, darkPath, whitePath);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        if (parentStage != null) {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
        }
        if (stageName.equals("Новая игра")) {
            stage.setResizable(false);
        }
        return stage;
    }
}

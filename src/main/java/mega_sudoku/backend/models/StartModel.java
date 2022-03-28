package mega_sudoku.backend.models;

import mega_sudoku.backend.utils.ColorThemeManager;
import mega_sudoku.frontend.controllers.StartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Класс, реализующий бизнес-логику стартового окна.
 */
public class StartModel {
    // Объект класса.
    private static StartModel INSTANCE;

    // Приватный конструктор.
    private StartModel() { }

    /**
     * Реализация простейшего паттенра синглетон.
     * @return инстанс класса.
     */
    public static StartModel getStartModel() {
        if (INSTANCE == null) {
            INSTANCE = new StartModel();
        }
        return INSTANCE;
    }

    /**
     * Генерация стартового окна программы.
     * @return сгенерированное стартовое окно.
     */
    public Stage buildStartScreen() {
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(StartController.class.getResource("/fxml_stages/start_screen.fxml"))));
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.getIcons().add(new Image("/icon.png"));
        ColorThemeManager.setThemeToScene(scene,
                Objects.requireNonNull(StartController.class.getResource("/styles/dark_start_screen.css")).toExternalForm(),
                Objects.requireNonNull(StartController.class.getResource("/styles/white_start_screen.css")).toExternalForm());
        return stage;
    }

    /**
     * Генерация дочернего окна.
     * @param parentStage родительское окно.
     * @param resource ссылка на fxml дочернего окна.
     * @param stageName название дочернего окна.
     * @param darkPath путь к css файлу с темной темой дочернего окна.
     * @param whitePath путь к css файлу со светлой темой дочернего окна.
     * @return сгенерированное дочернее окно.
     */
    public Stage buildChildStage(Stage parentStage, URL resource, String stageName, String darkPath, String whitePath) {
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
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(parentStage);
        if (stageName.equals("Новая игра")) {
            stage.setResizable(false);
        }
        return stage;
    }
}

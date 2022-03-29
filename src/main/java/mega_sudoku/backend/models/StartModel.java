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
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(StartController.class.getResource("/fxml_views/start_view.fxml"))));
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
}

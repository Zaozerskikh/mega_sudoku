package mega_sudoku.backend.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Утилитный класс, отвечающий за генерацию окон.
 */
public class CommonStageBuilder {
    /**
     * Генерация окна.
     * @param parentStage родительское окно.
     * @param resource ссылка на fxml дочернего окна.
     * @param stageName название дочернего окна.
     * @param darkPath путь к css файлу с темной темой дочернего окна.
     * @param whitePath путь к css файлу со светлой темой дочернего окна.
     * @return сгенерированное дочернее окно.
     */
    private static Stage buildStage(Stage parentStage, URL resource, String stageName, String darkPath, String whitePath) {
        Stage stage = new Stage();
        stage.setTitle(stageName);
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(resource)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert scene != null;
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
        if (stageName.equals("Мега Судоку")) {
            stage.setMinHeight(400);
            stage.setMinWidth(600);
        }
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        return stage;
    }

    /**
     * Фасад генерации стартового окна.
     * @return стартовое окно.
     */
    public static Stage buildStartStage() {
        return buildStage(null, CommonStageBuilder.class.getResource("/fxml_views/start_view.fxml"), "Мега Судоку",
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/dark_start_screen.css")).toExternalForm(),
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/white_start_screen.css")).toExternalForm());
    }

    /**
     * Фасад генерации окна информации о разработчиках.
     * @return окно информации о разработчиках.
     */
    public static Stage buildDevInfoStage(Stage parentStage) {
        return buildStage(parentStage, CommonStageBuilder.class.getResource("/fxml_views/dev_info_view.fxml"), "О разработчиках",
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/dark_info_screen.css")).toExternalForm(),
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/white_info_screen.css")).toExternalForm());
    }

    /**
     * Фасад генерации окна информации об игре.
     * @return окно информации об игре.
     */
    public static Stage buildHelpStage(Stage parentStage) {
        return buildStage(parentStage, CommonStageBuilder.class.getResource("/fxml_views/help_view.fxml"), "Помощь",
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/dark_info_screen.css")).toExternalForm(),
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/white_info_screen.css")).toExternalForm());
    }

    /**
     * Фасад генерации окна настроек новой игры.
     * @return окно настроек новой игры.
     */
    public static Stage buildNewGameSettingsStage() {
        return buildStage(null, CommonStageBuilder.class.getResource("/fxml_views/new_game_settings_view.fxml"), "Новая Игра",
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/dark_settings_screen.css")).toExternalForm(),
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/white_settings_screen.css")).toExternalForm());
    }

    /**
     * Фасад генерации окна progress bar.
     * @return окно progress bar.
     */
    public static Stage buildProgressBarStage() {
        return buildStage(null, CommonStageBuilder.class.getResource("/fxml_views/progress_check_view.fxml"), "Загрузка Игры",
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/dark_progress_bar.css")).toExternalForm(),
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/white_progress_bar.css")).toExternalForm());
    }
}

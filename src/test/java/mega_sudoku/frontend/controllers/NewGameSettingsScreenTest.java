package mega_sudoku.frontend.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mega_sudoku.backend.models.NewGameSettingsModel;
import mega_sudoku.backend.sudoku.DifficultyLevel;
import mega_sudoku.backend.utils.ColorThemeManager;
import mega_sudoku.backend.utils.CommonStageBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.util.NodeQueryUtils;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class NewGameSettingsScreenTest extends ApplicationTest {

    NewGameSettingsModel model;

    NewGameSettingsController controller;

    @Override
    public void start(Stage xstage) throws Exception {
        Stage stage = new Stage();
        var loader = new FXMLLoader(this.getClass().getResource("/fxml_views/new_game_settings_view.fxml"));
        stage.setTitle("Новая Игра");
        Scene scene = new Scene(loader.load());
        ColorThemeManager.setThemeToScene(scene,
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/dark_settings_screen.css")).toExternalForm(),
                Objects.requireNonNull(CommonStageBuilder.class.getResource("/styles/white_settings_screen.css")).toExternalForm());
        stage.getIcons().add(new Image("/icon.png"));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);

        // init model and controller with reflection.
        controller = loader.getController();
        var modelField = controller.getClass().getDeclaredField("model");
        modelField.setAccessible(true);
        model = (NewGameSettingsModel)modelField.get(controller);
        stage.show();
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void selected16Test() {
        assertAll(
                () -> assertEquals(16, model.getBoardSize()),
                () -> assertTrue(controller.box16.isSelected()),
                () -> assertFalse(controller.box25.isSelected())
        );
        clickOn("#box16");
        assertAll(
                () -> assertEquals(25, model.getBoardSize()),
                () -> assertTrue(controller.box25.isSelected()),
                () -> assertFalse(controller.box16.isSelected())
        );
        clickOn("#box16");
        assertAll(
                () -> assertEquals(16, model.getBoardSize()),
                () -> assertTrue(controller.box16.isSelected()),
                () -> assertFalse(controller.box25.isSelected())
        );
    }

    @Test
    void selected25Test() {
        assertAll(
                () -> assertEquals(16, model.getBoardSize()),
                () -> assertTrue(controller.box16.isSelected()),
                () -> assertFalse(controller.box25.isSelected())
        );
        clickOn("#box25");
        assertAll(
                () -> assertEquals(25, model.getBoardSize()),
                () -> assertTrue(controller.box25.isSelected()),
                () -> assertFalse(controller.box16.isSelected())
        );
        clickOn("#box25");
        assertAll(
                () -> assertEquals(16, model.getBoardSize()),
                () -> assertTrue(controller.box16.isSelected()),
                () -> assertFalse(controller.box25.isSelected())
        );
    }

    @Test
    void sliderEasyToMediumTest() throws InterruptedException {
        assertEquals(DifficultyLevel.EASY, model.getDifficultyLevel());
        moveTo(controller.easyLabel)
                .moveBy(0, -27)
                .press(MouseButton.PRIMARY)
                .moveBy(150, 0)
                .release(MouseButton.PRIMARY);
        Thread.sleep(100);
        assertAll(
                () -> assertEquals( DifficultyLevel.MEDIUM, model.getDifficultyLevel()),
                () -> assertEquals(1, controller.mediumLabel.opacityProperty().doubleValue()),
                () -> assertEquals(0.6, controller.easyLabel.opacityProperty().doubleValue()),
                () -> assertEquals(0.6, controller.hardLabel.opacityProperty().doubleValue())
        );
    }

    @Test
    void sliderEasyToHardTest() throws InterruptedException {
        assertEquals(DifficultyLevel.EASY, model.getDifficultyLevel());
        moveTo(controller.easyLabel)
                .moveBy(0, -27)
                .press(MouseButton.PRIMARY)
                .moveBy(300, 0)
                .release(MouseButton.PRIMARY);
        Thread.sleep(100);
        assertAll(
                () -> assertEquals( DifficultyLevel.HARD, model.getDifficultyLevel()),
                () -> assertEquals(0.6, controller.mediumLabel.opacityProperty().doubleValue()),
                () -> assertEquals(0.6, controller.easyLabel.opacityProperty().doubleValue()),
                () -> assertEquals(1, controller.hardLabel.opacityProperty().doubleValue())
        );
    }

    @Test
    void sliderHardToMedium() throws InterruptedException {
        assertEquals(DifficultyLevel.EASY, model.getDifficultyLevel());
        moveTo(controller.hardLabel)
                .moveBy(-30, -24)
                .press(MouseButton.PRIMARY);
        assertEquals(DifficultyLevel.HARD, model.getDifficultyLevel());
        moveBy(-150, 0)
                .release(MouseButton.PRIMARY);
        Thread.sleep(100);
        assertAll(
                () -> assertEquals( DifficultyLevel.MEDIUM, model.getDifficultyLevel()),
                () -> assertEquals(1, controller.mediumLabel.opacityProperty().doubleValue()),
                () -> assertEquals(0.6, controller.easyLabel.opacityProperty().doubleValue()),
                () -> assertEquals(0.6, controller.hardLabel.opacityProperty().doubleValue())
        );
    }

    @Test
    void sliderHardToEasy() throws InterruptedException {
        assertEquals(DifficultyLevel.EASY, model.getDifficultyLevel());
        moveTo(controller.hardLabel)
                .moveBy(-30, -24)
                .press(MouseButton.PRIMARY)
                .moveBy(-200, 0)
                .release(MouseButton.PRIMARY);
        Thread.sleep(100);
        assertAll(
                () -> assertEquals( DifficultyLevel.EASY, model.getDifficultyLevel()),
                () -> assertEquals(0.6, controller.mediumLabel.opacityProperty().doubleValue()),
                () -> assertEquals(1, controller.easyLabel.opacityProperty().doubleValue()),
                () -> assertEquals(0.6, controller.hardLabel.opacityProperty().doubleValue())
        );
    }

    @Test
    void onCloseButtonClickTest() throws Exception {
        clickOn(((GridPane)((Pane)NodeQueryUtils.rootOfWindow(window("Новая Игра")).toArray()[0]).getChildren().get(10)).getChildren().get(0));
        Thread.sleep(100);
        assertThrows(
                NoSuchElementException.class,
                () -> window("Новая Игра")
        );
        FxAssert.verifyThat(window("Мега Судоку"), WindowMatchers.isShowing());
    }

    @Test
    void onMinimizeButtonClick() throws Exception {
        clickOn(((GridPane)((Pane)NodeQueryUtils.rootOfWindow(window("Новая Игра")).toArray()[0]).getChildren().get(10)).getChildren().get(2));
        Thread.sleep(100);
        assertTrue(((Stage)window("Новая Игра")).isIconified());
    }


    @Test
    void onNewGameButtonClickTest() throws InterruptedException {
        clickOn(controller.startGameButton);
        Thread.sleep(100);
        assertThrows(
                NoSuchElementException.class,
                () -> window("Новая Игра")
        );
        FxAssert.verifyThat(window("Загрузка Игры"), WindowMatchers.isShowing());
        Thread.sleep(8000);
        assertThrows(
                NoSuchElementException.class,
                () -> window("Загрузка Игры")
        );
        FxAssert.verifyThat(window("Игра"), WindowMatchers.isShowing());
    }
}
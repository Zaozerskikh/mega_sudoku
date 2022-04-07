package mega_sudoku.frontend.controllers;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mega_sudoku.backend.utils.ColorThemeManager;
import mega_sudoku.backend.utils.CommonStageBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.util.NodeQueryUtils;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class StatScreenTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        stage = CommonStageBuilder.buildStartStage();
        stage.show();
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void onNewGameButtonClickTest() {
        clickOn(((GridPane)NodeQueryUtils.rootOfWindow(window("Мега Судоку")).toArray()[0]).getChildren().get(1));
        FxAssert.verifyThat(window("Новая Игра"), WindowMatchers.isShowing());
    }

    @Test
    void onHelpButtonClickTest() {
        clickOn(((GridPane)NodeQueryUtils.rootOfWindow(window("Мега Судоку")).toArray()[0]).getChildren().get(3));
        FxAssert.verifyThat(window("Помощь"), WindowMatchers.isShowing());
    }

    @Test
    void onInfoButtonClickTest() {
        clickOn(((GridPane)NodeQueryUtils.rootOfWindow(window("Мега Судоку")).toArray()[0]).getChildren().get(4));
        FxAssert.verifyThat(window("О разработчиках"), WindowMatchers.isShowing());
    }

    @Test
    void onDarkThemeButtonClickTest() throws InterruptedException {
        var darkThemeButton = ((GridPane)((GridPane)NodeQueryUtils.rootOfWindow(window("Мега Судоку"))
                .toArray()[0]).getChildren().get(0)).getChildren().get(1);
        // white -> dark.
        clickOn(darkThemeButton);
        Thread.sleep(100);
        assertTrue(ColorThemeManager.isDarkTheme());
        assertTrue(((GridPane) NodeQueryUtils.rootOfWindow(window("Мега Судоку")).toArray()[0])
                .getStylesheets().stream().anyMatch(x -> Arrays.stream(x.split("/"))
                        .toList().contains("dark_start_screen.css")));

        // dark -> white.
        clickOn(darkThemeButton);
        Thread.sleep(100);
        assertFalse(ColorThemeManager.isDarkTheme());
        assertTrue(((GridPane) NodeQueryUtils.rootOfWindow(window("Мега Судоку")).toArray()[0])
                .getStylesheets().stream().anyMatch(x -> Arrays.stream(x.split("/"))
                        .toList().contains("white_start_screen.css")));
    }

    @Test
    void onMinimizeButtonClick() throws InterruptedException {
        clickOn(((GridPane)((GridPane)NodeQueryUtils.rootOfWindow(window("Мега Судоку"))
                .toArray()[0]).getChildren().get(5)).getChildren().get(2));
        Thread.sleep(100);
        assertTrue(((Stage)window("Мега Судоку")).isIconified());
    }

    Node closeButton, yes, no;

    private void initDialogButton() {
        closeButton = ((GridPane)((GridPane)NodeQueryUtils.rootOfWindow(window("Мега Судоку"))
                .toArray()[0]).getChildren().get(5)).getChildren().get(0);
    }

    @Test
    void onCloseButtonClickExitTest() throws InterruptedException {
        initDialogButton();
        clickOn(closeButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());

        // Click yes.
        clickOn(((Pane)((GridPane)NodeQueryUtils.rootOfWindow(window("dialog"))
                .toArray()[0]).getChildren().get(1)).getChildren().get(1));

        Thread.sleep(100);
        assertAll(
                () -> assertThrows(
                        NoSuchElementException.class,
                        () -> window("dialog")
                ),
                () -> assertThrows(
                        NoSuchElementException.class,
                        () -> window("Мега Судоку")
                )
        );
    }

    @Test
    void onCloseButtonClickCancelledByNoTest() throws InterruptedException {
        initDialogButton();
        clickOn(closeButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());

        // Click no.
        clickOn(((Pane)((GridPane)NodeQueryUtils.rootOfWindow(window("dialog"))
                .toArray()[0]).getChildren().get(1)).getChildren().get(2));

        Thread.sleep(100);
        assertThrows(
                NoSuchElementException.class,
                () -> window("dialog")
        );
        FxAssert.verifyThat(window("Мега Судоку"), WindowMatchers.isShowing());
    }

    @Test
    void onCloseButtonClickCancelledByXTest() throws InterruptedException {
        initDialogButton();
        clickOn(closeButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());

        // Click exit dialog.
        clickOn(((Pane)((GridPane)NodeQueryUtils.rootOfWindow(window("dialog"))
                .toArray()[0]).getChildren().get(0)).getChildren().get(1));

        Thread.sleep(100);
        assertThrows(
                NoSuchElementException.class,
                () -> window("dialog")
        );
        FxAssert.verifyThat(window("Мега Судоку"), WindowMatchers.isShowing());
    }
}
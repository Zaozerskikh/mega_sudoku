package mega_sudoku.frontend.controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mega_sudoku.backend.game.GameGridBuilder;
import mega_sudoku.backend.models.GameModel;
import mega_sudoku.backend.sudoku.DifficultyLevel;
import mega_sudoku.backend.sudoku.SudokuBuilder;
import mega_sudoku.backend.utils.ColorThemeManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.util.NodeQueryUtils;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционные тесты для окна с игрой.
 */
class GameScreenTest extends ApplicationTest {
    // объект модели.
    GameModel model;

    // объект контроллера.
    GameController controller;

    @Override
    public void start(Stage xstage) throws Exception {
        SudokuBuilder.getSudokuBuilder().generateSudoku(16, DifficultyLevel.EASY);
        var sudoku = SudokuBuilder.getSudokuBuilder().getGeneratedSudoku();
        GameGridBuilder.getBuilder().buildGameGrid(sudoku.getBoardSize(), sudoku.getCurrentPosition());
        var gameGrid = GameGridBuilder.getBuilder().getGeneratedPane();
        Stage stage = new Stage();
        stage.setTitle("Игра");
        var loader = new FXMLLoader(this.getClass().getResource("/fxml_views/game_view.fxml"));
        Scene scene = new Scene(loader.load());
        gameGrid.setPadding(new Insets(11));
        ((GridPane)(scene.getRoot().getChildrenUnmodifiable().get(0))).add(gameGrid, 0, 0);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        ColorThemeManager.setThemeToScene(stage.getScene(),
                Objects.requireNonNull(SudokuBuilder.class.getResource("/styles/dark_game_screen.css")).toExternalForm(),
                Objects.requireNonNull(SudokuBuilder.class.getResource("/styles/white_game_screen.css")).toExternalForm());
        String diffInfo = (sudoku.getDiffLevel() == DifficultyLevel.EASY) ?
                "Простой" : (sudoku.getDiffLevel() == DifficultyLevel.MEDIUM) ?
                "Средний" : "Сложный";
        ((Label)((GridPane)scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(4)).
                setText("Мега-Cудоку " + sudoku.getBoardSize()
                        + " x " + sudoku.getBoardSize()
                        + " " + diffInfo);
        stage.getIcons().add(new Image("/icon.png"));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        // init model and controller.
        controller = loader.getController();
        model = controller.getGameModel();
        stage.show();
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    /**
     * Заполнение поля некорректными цифрами и попытка проверки решения.
     * Тестирование вывода сообщения об ошибке.
     * Тестирование закрашивания некорректных клеток красным цветом.
     */
    @Test
    void incorrectFillingTest() {
        controller.getGameBoard().getChildren().forEach(x -> {
            if (x.getClass() == TextField.class && ((TextField)x).isEditable()) {
                clickOn(x).type(KeyCode.DIGIT1);
            }
        });
        clickOn(controller.checkButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());
        assertAll(
                () -> assertEquals("\nПоле заполнено с ошибками :(", ((Label) ((Pane) ((GridPane) NodeQueryUtils.rootOfWindow(window("dialog")).toArray()[0])
                        .getChildren().get(1)).getChildren().get(0)).getText()),
                () -> controller.getGameBoard().getChildren().stream()
                        .filter(x -> x.getClass() == TextField.class)
                        .forEach(x -> {
                            if (((TextField)x).getText().equals(String.valueOf(model.getSudoku()
                                    .getSolution()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)]))) {
                                assertNotEquals(Color.RED, ((TextField)x).getBackground().getFills().get(0).getFill());
                            }
                        })
        );
    }


    /**
     * Попытка проверки решения с пустыми клетками.
     * Тестирование вывода ифнормационного сообщения об ошибке.
     * Тестирование закрашивания красным цветом пустых клеток.
     */
    @Test
    void notCompleteCellsTest() {
        clickOn(controller.checkButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());
        assertAll(
                () -> assertEquals("\nНе все клетки заполнены.",
                        ((Label) ((Pane) ((GridPane) NodeQueryUtils.rootOfWindow(window("dialog")).toArray()[0])
                                .getChildren().get(1)).getChildren().get(0)).getText()),
                () -> controller.getGameBoard().getChildren().stream()
                        .filter(x -> x.getClass() == TextField.class)
                        .forEach(x -> {
                            if (((TextField)x).getText().equals("")) {
                                assertEquals(Color.RED, ((TextField)x).getBackground().getFills().get(0).getFill());
                            } else {
                                assertNotEquals(Color.RED, ((TextField)x).getBackground().getFills().get(0).getFill());
                            }
                        })
        );
    }


    /**
     * Корректное заполнение игровой сетки судоку и проверка решения.
     * Тестирование вывода информационного сообщения с успехом.
     */
    @Test
    void correctFillingTest() {
        controller.getGameBoard().getChildren().forEach(x -> {
            if (x.getClass() == TextField.class && ((TextField)x).isEditable()) {
                int answ = model.getSudoku().getSolution()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)];
                clickOn(x).write(String.valueOf(answ));
            }
        });
        clickOn(controller.checkButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());
        assertEquals("\nСудоку решена верно!", ((Label) ((Pane) ((GridPane) NodeQueryUtils.rootOfWindow(window("dialog")).toArray()[0])
                .getChildren().get(1)).getChildren().get(0)).getText());
    }


    /**
     * Попытка получения подсказки.
     * Тестирование поведения при попытке вызвать подсказу без выбранной клетки.
     * Тестирование показа подсказки на экране.
     * Тестирование корректности подсказки.
     * Тестирование обновления текущей позиции на доске в объекте модели.
     * Тестирование закрашивания клетки с подсказкой зеленым цветом.
     * Тестирование автоматического возврата исходного цвета клетки.
     */
    @Test
    @SuppressWarnings("all")
    void hintTest() throws InterruptedException {
        clickOn(controller.helpButton);
        var cell = (TextField)controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class && ((TextField)x).isEditable())
                .findAny().get();
        clickOn(cell);
        clickOn(controller.helpButton);
        assertAll(
                () -> assertEquals(
                        model.getSudoku().getSolution()[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)],
                        Integer.parseInt(cell.getText())
                ),
                () -> assertEquals(Color.GREEN, cell.getBackground().getFills().get(0).getFill()),
                () -> assertEquals(
                        model.getSudoku().getSolution()[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)],
                        model.getSudoku().getCurrentPosition()[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)]
                )
        );
        Thread.sleep(5000);
        assertNotEquals(Color.GREEN, cell.getBackground().getFills().get(0).getFill());
    }


    /**
     * Заполнение клетки игрового поля валидным значением.
     * Тестирование отсутствия сообщений об ошибке на экране.
     * Тестирование обновления текущей позиции в объекте модели.
     */
    @Test
    @SuppressWarnings("all")
    void cellValidValueTest() {
        var cell = (TextField)controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class && ((TextField)x).isEditable())
                .findAny().get();
        clickOn(cell).write("10").clickOn(cell);
        assertAll(
                () -> assertEquals("10", cell.selectedTextProperty().get()),
                () -> assertEquals(10, model.getSudoku().getCurrentPosition()[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)])
        );
    }


    /**
     * Попытка ввода в клетку невалидного значения.
     * Ввод строки.
     * Тестирование показа информационного сообщения об ошибке.
     * Ввод слишком большого числового значения.
     * Тестирование показа информационного сообщения об ошибке.
     * Тестирования отсутствия обновления текущей позиции в объекте модели.
     */
    @Test
    @SuppressWarnings("all")
    void cellInvalidValueTest() {
        var cell = (TextField)controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class && ((TextField)x).isEditable())
                .findAny().get();

        // string val test.
        clickOn(cell).write("abc").clickOn(cell);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());
        assertAll(
                () -> assertEquals("\nНекорректное значение поля.", ((Label) ((Pane) ((GridPane) NodeQueryUtils
                        .rootOfWindow(window("dialog")).toArray()[0])
                        .getChildren().get(1)).getChildren().get(0)).getText()
                ),
                () -> assertEquals("abc", cell.selectedTextProperty().get())
        );
        clickOn(
                ((Pane) ((GridPane) NodeQueryUtils
                        .rootOfWindow(window("dialog")).toArray()[0])
                        .getChildren().get(1)).getChildren().get(2)
        );

        // too big int value test.
        write(String.valueOf(model.getSudoku().getBoardSize() + 1)).clickOn(cell);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());
        assertAll(
                () -> assertEquals("\nНекорректное значение поля.", ((Label) ((Pane) ((GridPane) NodeQueryUtils
                        .rootOfWindow(window("dialog")).toArray()[0])
                        .getChildren().get(1)).getChildren().get(0)).getText()
                ),
                () -> assertEquals("17", cell.selectedTextProperty().get()),
                () -> assertNotEquals(17, model.getSudoku().getCurrentPosition()[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)])
        );
    }

    /**
     * Отображение полного решения головоломки.
     * Тестирование показа предупреждения.
     * Тестирования корректности отображенного решения.
     * Тестирование обновления текущей позиции в объекте model.
     */
    @Test
    void checkSolutionTest() {
        clickOn(controller.solutionButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());
        clickOn(
                ((Pane) ((GridPane) NodeQueryUtils
                .rootOfWindow(window("dialog")).toArray()[0])
                .getChildren().get(1)).getChildren().get(1)
        );
        controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class)
                .forEach(x -> assertAll(
                        () -> assertEquals(
                                model.getSudoku().getSolution()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)],
                                model.getSudoku().getCurrentPosition()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)]
                        ),
                        () -> assertEquals(
                                model.getSudoku().getSolution()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)],
                                Integer.parseInt(((TextField)x).getText())
                        )
                ));
    }


    /**
     * Рандомное заполнение поля и его сброс обратно.
     * Тестирование показа предупреждения ресета.
     * Тестирование ресета текущей позиции в объекте модели.
     */
    @Test
    @SuppressWarnings("all")
    void resetUserSolutionTest() {
        // Random fill.
        IntStream.range(0, 10).forEach(idx -> {
            var field = (TextField)controller.getGameBoard().getChildren().stream()
                    .filter(x -> x.getClass() == TextField.class)
                    .filter(x -> ((TextField)x).getText().equals(""))
                    .findAny().get();
            clickOn(field).write("1");
        });

        // reset dialog.
        clickOn(controller.resetButton);
        FxAssert.verifyThat(window("dialog"), WindowMatchers.isShowing());
        clickOn(
                ((Pane) ((GridPane) NodeQueryUtils
                        .rootOfWindow(window("dialog")).toArray()[0])
                        .getChildren().get(1)).getChildren().get(1)
        );

        // reset
        controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class)
                .forEach(x -> {
                    if (model.getSudoku().getCurrentPosition()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)]
                            != model.getSudoku().getProblem()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)]) {
                        throw new AssertionError("position != problem");
                    }
                    if (model.getSudoku().getCurrentPosition()[GridPane.getColumnIndex(x)][GridPane.getRowIndex(x)] == -1
                            && !((TextField)x).getText().equals("")) {
                        throw new AssertionError("problem != pos on board");
                    }
                });
    }


    /**
     * Дабл-клик по рандомной клетке с цифрой.
     * Тестирование подсвечивания всех аналогичных цифр на игровом поле.
     * Тестирование очистки подсветки клеток после клика в другом месте.
     */
    @Test
    @SuppressWarnings("all")
    void equalNumsColoringTest() {
        // fill
        var target = controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class)
                .filter(x -> !((TextField)x).getText().equals(""))
                .findAny().get();
        doubleClickOn(target);

        controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class)
                .forEach(x -> {
                    if (((TextField)x).getText().equals(((TextField)target).getText())) {
                        assertEquals(Color.DARKORANGE, ((TextField)x).getBackground().getFills().get(0).getFill());
                    } else {
                        assertNotEquals(Color.DARKORANGE, ((TextField)x).getBackground().getFills().get(0).getFill());
                    }
                });

        // reset
        var newTarget = controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class)
                .filter(x -> !((TextField)x).getText().equals(""))
                .filter(x -> !Objects.equals(GridPane.getRowIndex(x), GridPane.getRowIndex(target)))
                .findAny().get();
        clickOn(target);

        controller.getGameBoard().getChildren().stream()
                .filter(x -> x.getClass() == TextField.class)
                .forEach(x -> assertNotEquals(Color.DARKORANGE,
                        ((TextField)x).getBackground().getFills().get(0).getFill()));
    }


    /**
     * Развертывание окна с игрой на полный экран.
     * Тестирование ресайза окна.
     * Выход из полноэкранного режима.
     * Тестирование ресайза окна.
     */
    @Test
    void maximizeTest() {
        clickOn(controller.maximizeButton);
        assertTrue(((Stage)window("Игра")).isFullScreen());
        clickOn(controller.maximizeButton);
        assertFalse(((Stage)window("Игра")).isFullScreen());
    }
}
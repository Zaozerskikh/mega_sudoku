package mega_sudoku.backend.game;

import mega_sudoku.backend.models.StartModel;
import mega_sudoku.backend.sudoku.Sudoku;
import mega_sudoku.backend.sudoku.SudokuBuilder;
import mega_sudoku.backend.utils.ColorThemeManager;
import mega_sudoku.frontend.controllers.StartController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс, отвечающий за генерацию окна с игрой.
 */
public class GameStageBuilder {
    /**
     * Генерирует и отображает окно с игрой.
     * @param sudoku судоку для данной игры.
     */
    public static void generateAndShowGameStage(Sudoku sudoku) {
        GameGridBuilder.getBuilder().buildGameGrid(sudoku.getBoardSize(), sudoku.getCurrentPosition());
        var gameGrid = GameGridBuilder.getBuilder().getGeneratedPane();
        Stage stage = new Stage();
        Scene scene ;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(StartController.class.getResource("/fxml_views/game_view.fxml"))));
        } catch (IOException e) {
            StartModel.getStartModel().buildStartScreen();
            return;
        }
        gameGrid.setPadding(new Insets(11));
        ((GridPane)(scene.getRoot().getChildrenUnmodifiable().get(0))).add(gameGrid, 0, 0);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        ColorThemeManager.setThemeToScene(stage.getScene(),
                Objects.requireNonNull(SudokuBuilder.class.getResource("/styles/dark_game_screen.css")).toExternalForm(),
                Objects.requireNonNull(SudokuBuilder.class.getResource("/styles/white_game_screen.css")).toExternalForm());
        String diffInfo = (sudoku.getDiffLevel() == 1) ?
                "Простой" : (sudoku.getDiffLevel() == 2) ?
                "Средний" : "Сложный";
        ((Label)((GridPane)scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(4)).
                setText("Мега-Cудоку " + sudoku.getBoardSize()
                        + " x " + sudoku.getBoardSize()
                        + " " + diffInfo);
        stage.getIcons().add(new Image("/icon.png"));
        stage.show();
    }
}

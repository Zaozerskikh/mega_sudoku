package mega_sudoku.main;

import javafx.application.Application;
import javafx.stage.Stage;
import mega_sudoku.backend.utils.CommonStageBuilder;

/**
 * Главный класс приложения, который отвечает за первичный запуск.
 */
public class MainAppClass extends Application {
    @Override
    public void start(Stage stage) {
        CommonStageBuilder.buildStartStage().show();
    }

    public static void main(String[] args) {
        launch();
    }
}
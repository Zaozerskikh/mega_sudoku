package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.StartModel;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Главный класс приложения, который отвечает за первичный запуск.
 */
public class MainAppClass extends Application {
    @Override
    public void start(Stage stage) {
        StartModel.getStartModel().buildStartScreen().show();
    }

    public static void main(String[] args) {
        launch();
    }
}
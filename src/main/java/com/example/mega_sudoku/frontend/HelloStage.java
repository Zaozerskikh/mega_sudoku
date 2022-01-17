package com.example.mega_sudoku.frontend;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloStage extends Application {
    @Override
    public void start(Stage stage) {
        HelloController.returnStartScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}
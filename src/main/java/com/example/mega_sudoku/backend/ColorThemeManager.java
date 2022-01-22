package com.example.mega_sudoku.backend;

import com.example.mega_sudoku.frontend.HelloController;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;

public class ColorThemeManager {
    private static boolean darkTheme = false;

    public static boolean isDarkTheme() {
        return darkTheme;
    }

    public static void switchTheme() {
        if (isDarkTheme()) {
            darkTheme = false;
        } else {
            darkTheme = true;
        }
    }

    public static void setThemeToDialogPane(DialogPane dp) {
        if(ColorThemeManager.isDarkTheme()) {
            dp.getStylesheets().add(HelloController.class.getResource("/styles/dark_dialog_pane.css").toExternalForm());
        } else {
            dp.getStylesheets().add(HelloController.class.getResource("/styles/white_dialog_pane.css").toExternalForm());
        }
    }

    public static void setThemeToScene(Scene scene, String darkPath, String whitePath) {
        scene.getRoot().getStylesheets().clear();
        scene.getStylesheets().clear();
        if (ColorThemeManager.isDarkTheme()) {
            scene.getRoot().getStylesheets().add(darkPath);
        } else {
            scene.getRoot().getStylesheets().add(whitePath);
        }
    }
}

package mega_sudoku.backend.utils;

import javafx.scene.Scene;

/**
 * Класс, контролирующий изменения цветовой темы приложения.
 */
public class ColorThemeManager {
    // Включена ли на данный момент тёмная тема или нет.
    private static boolean darkTheme = false;

    /**
     * Getter для darkTheme.
     * @return установлена ли темная тема.
     */
    public static boolean isDarkTheme() {
        return darkTheme;
    }

    /**
     * Переключение темы со светлой на темную и обратно.
     */
    public static void switchTheme() {
        darkTheme = !isDarkTheme();
    }

    /**
     * Установка соответствующей темы окна.
     * @param scene окно.
     * @param darkPath путь к css файлу с темной темой.
     * @param whitePath путь к css файлу со светлой темой.
     */
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

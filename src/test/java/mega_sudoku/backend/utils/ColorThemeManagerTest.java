package mega_sudoku.backend.utils;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ColorThemeManagerTest extends ApplicationTest {

    @Test
    void switchThemeTest() {
        boolean first = ColorThemeManager.isDarkTheme();
        ColorThemeManager.switchTheme();
        assertNotEquals(first, ColorThemeManager.isDarkTheme());
        ColorThemeManager.switchTheme();
    }
}
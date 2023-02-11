package mega_sudoku.backend.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuBuilderTest {
    @Test
    void getEmptyCellsCount16EASYTest() {
        checkEmptyCellsCount(16, DifficultyLevel.EASY);
    }

    @Test
    void getEmptyCellsCount25EASYTest() {
        checkEmptyCellsCount(25, DifficultyLevel.EASY);
    }

    @Test
    void getEmptyCellsCount16MEDIUMTest() {
        checkEmptyCellsCount(16, DifficultyLevel.MEDIUM);
    }

    @Test
    void getEmptyCellsCount25MEDIUMTest() {
        checkEmptyCellsCount(25, DifficultyLevel.MEDIUM);
    }

    @Test
    void getEmptyCellsCount16HARDTest() {
        checkEmptyCellsCount(16, DifficultyLevel.HARD);
    }

    @Test
    void getEmptyCellsCount25HARDTest() {
        checkEmptyCellsCount(25, DifficultyLevel.HARD);
    }

    private void checkEmptyCellsCount(int boardSize, DifficultyLevel difficultyLevel) {
        SudokuBuilder builder = SudokuBuilder.getSudokuBuilder();
        builder.generateSudoku(boardSize, difficultyLevel);
        int count = 0;
        for (int[] row : builder.getGeneratedSudoku().getProblem()) {
            count += Arrays.stream(row).filter(number -> number == -1).count();
        }
        assertEquals(builder.getEmptyCellsCount(), count);
    }
}
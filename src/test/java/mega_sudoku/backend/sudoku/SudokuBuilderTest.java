package mega_sudoku.backend.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBuilderTest {
    SudokuBuilder builder;

    @BeforeEach
    void setUp() {
        builder = SudokuBuilder.getSudokuBuilder();
        //builder.generateSudoku();
    }

    @Test
    void getCount16EASYTest() {
        checkCount(16, DifficultyLevel.EASY);
    }

    @Test
    void getCount25EASYTest() {
        checkCount(25, DifficultyLevel.EASY);
    }

    @Test
    void getCount16MEDIUMTest() {
        checkCount(16, DifficultyLevel.MEDIUM);
    }

    @Test
    void getCount25MEDIUMTest() {
        checkCount(25, DifficultyLevel.MEDIUM);
    }

    @Test
    void getCount16HARDTest() {
        checkCount(16, DifficultyLevel.HARD);
    }

    @Test
    void getCount25HARDTest() {
        checkCount(25, DifficultyLevel.HARD);
    }

    private void checkCount(int boardSize, DifficultyLevel difficultyLevel) {
        SudokuBuilder builder = SudokuBuilder.getSudokuBuilder();
        builder.generateSudoku(boardSize, difficultyLevel);
        int count = 0;
        for (int[] row : builder.getGeneratedSudoku().getProblem()) {
            for (int number : row) {
                if (number == -1) {
                    count++;
                }
            }
        }
        assertEquals(builder.getCount(), count);
    }
}
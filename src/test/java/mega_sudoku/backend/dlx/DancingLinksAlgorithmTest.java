package mega_sudoku.backend.dlx;

import mega_sudoku.backend.sudoku.DifficultyLevel;
import mega_sudoku.backend.sudoku.SudokuBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DancingLinksAlgorithmTest {

    @Test
    void solve16EASYTest() {
        checkSudokuSolving(16, DifficultyLevel.EASY);
    }

    @Test
    void solve25EASYTest() {
        checkSudokuSolving(25, DifficultyLevel.EASY);
    }

    @Test
    void solve16MEDIUMTest() {
        checkSudokuSolving(16, DifficultyLevel.MEDIUM);
    }

    @Test
    void solve25MEDIUMTest() {
        checkSudokuSolving(25, DifficultyLevel.MEDIUM);
    }

    @Test
    void solve16HARDTest() {
        checkSudokuSolving(16, DifficultyLevel.HARD);
    }

    @Test
    void solve25HARDTest() {
        checkSudokuSolving(25, DifficultyLevel.HARD);
    }

    private void checkSudokuSolving(int boardSize, DifficultyLevel difficultyLevel) {
        SudokuBuilder builder = SudokuBuilder.getSudokuBuilder();
        builder.generateSudoku(boardSize, difficultyLevel);
        DancingLinksAlgorithm dancingLinksAlgorithm = new DancingLinksAlgorithm(builder.getGeneratedSudoku().getProblem());
        dancingLinksAlgorithm.solve();
        assertTrue(dancingLinksAlgorithm.getIfOnlyOneSolution());
    }
}
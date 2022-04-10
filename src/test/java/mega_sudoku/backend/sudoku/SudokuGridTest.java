package mega_sudoku.backend.sudoku;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuGridTest {
    @Test
    void sudokuGrid16Test() {
        checkSudokuGrid(new SudokuGrid(16).getMixedGrid());
    }

    @Test
    void sudokuGrid25Test() {
        checkSudokuGrid(new SudokuGrid(25).getMixedGrid());
    }

    private void checkSudokuGrid(int[][] board) {
        int boardSize = board.length;
        int sectorSize = (int)Math.sqrt(boardSize);

        var fragments = new ArrayList<HashSet<Integer>>();
        IntStream.range(0, boardSize).forEach(idx -> fragments.add(new HashSet<>()));

        // verticals.
        IntStream.range(0, boardSize)
                .forEach(i -> IntStream.range(0, boardSize)
                        .forEach(j -> fragments.get(i).add(board[i][j])));
        fragments.forEach(x -> {
            assertEquals(x.size(), boardSize);
            x.clear();
        });

        // horizontals.
        IntStream.range(0, boardSize)
                .forEach(i -> IntStream.range(0, boardSize)
                        .forEach(j -> fragments.get(j).add(board[i][j])));
        fragments.forEach(x -> {
            assertEquals(x.size(), boardSize);
            x.clear();
        });

        // sectors.
        for (int sector_i = 0; sector_i < boardSize; sector_i += sectorSize) {
            for (int sector_j = 0; sector_j < boardSize; sector_j += sectorSize) {
                for (int i = 0; i < sectorSize; ++i) {
                    for (int j = 0; j < sectorSize; ++j) {
                        fragments.get(sector_i + sector_j / sectorSize).add(board[sector_i + i][sector_j + j]);
                    }
                }
            }
        }

        fragments.forEach(x -> {
            assertEquals(x.size(), boardSize);
            x.clear();
        });
    }
}
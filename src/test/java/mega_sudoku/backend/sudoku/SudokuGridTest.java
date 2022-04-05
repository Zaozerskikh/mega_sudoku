package mega_sudoku.backend.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGridTest {
    int[][] grid16, grid25;

    @BeforeEach
    void setUp() {
        grid16 = new SudokuGrid(16).getMixedGrid(10);
        grid25 = new SudokuGrid(25).getMixedGrid(10);
    }

    @Test
    void checkMixedGrid25Rows() {
        boolean checkFlag = true;
        for (int[] row : grid25) {
            for (int num : row) {
                if (Arrays.stream(row).filter(n -> n == num).count() != 1) checkFlag = false;
            }
        }
        assertTrue(checkFlag);
    }

    @Test
    void checkMixedGrid16Rows() {
        boolean checkFlag = true;
        for (int[] row : grid16) {
            for (int num : row) {
                if (Arrays.stream(row).filter(n -> n == num).count() != 1) checkFlag = false;
            }
        }
        assertTrue(checkFlag);
    }

    @Test
    void checkMixedGrid16Columns() {
        boolean checkFlag = true;
        for (int i = 0; i < grid16.length; i++) {
            for (int j = i + 1; j < grid16[i].length; j++) {
                int tmp = grid16[i][j];
                grid16[i][j] = grid16[j][i];
                grid16[j][i] = tmp;
            }
        }
        for (int[] row : grid16) {
            for (int num : row) {
                if (Arrays.stream(row).filter(n -> n == num).count() != 1) checkFlag = false;
            }
        }
        assertTrue(checkFlag);
    }

    @Test
    void checkMixedGrid25Columns() {
        boolean checkFlag = true;
        for (int i = 0; i < grid25.length; i++) {
            for (int j = i + 1; j < grid25[i].length; j++) {
                int tmp = grid25[i][j];
                grid25[i][j] = grid25[j][i];
                grid25[j][i] = tmp;
            }
        }
        for (int[] row : grid25) {
            for (int num : row) {
                if (Arrays.stream(row).filter(n -> n == num).count() != 1) checkFlag = false;
            }
        }
        assertTrue(checkFlag);
    }

    @Test
    void checkMixedGrid25Sectors() {
        boolean checkFlag = true;
        int size = 25;
        int sectorSize = (int)Math.sqrt(size);
        int[][][] sectors = new int[size][sectorSize][sectorSize];
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                sectors[i][j] = Arrays.copyOfRange(grid25[i], sectorSize * j, sectorSize * (j  + 1));
            }
        }
        for (int[][] sector : sectors) {
            int[] snake = new int[size];
            int count = 0;
            for (int[] row : sector) {
                for (int num : row) {
                    snake[count++] = num;
                }
            }
            for (int num : snake) {
                if (Arrays.stream(snake).filter(n -> n == num).count() != 1) checkFlag = false;
            }
        }
        assertTrue(checkFlag);
    }

    @Test
    void checkMixedGrid16Sectors() {
        boolean checkFlag = true;
        int size = 16;
        int sectorSize = (int)Math.sqrt(size);
        int[][][] sectors = new int[size][sectorSize][sectorSize];
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                sectors[i][j] = Arrays.copyOfRange(grid16[i], sectorSize * j, sectorSize * (j  + 1));
            }
        }
        for (int[][] sector : sectors) {
            int[] snake = new int[size];
            int count = 0;
            for (int[] row : sector) {
                for (int num : row) {
                    snake[count++] = num;
                }
            }
            for (int num : snake) {
                if (Arrays.stream(snake).filter(n -> n == num).count() != 1) checkFlag = false;
            }
        }
        assertTrue(checkFlag);
    }
}
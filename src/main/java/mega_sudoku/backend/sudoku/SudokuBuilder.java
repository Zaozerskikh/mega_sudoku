package mega_sudoku.backend.sudoku;

import mega_sudoku.backend.dlx.DLXAlgoStarter;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс, отвечающий за генерацию судоку.
 */
public class SudokuBuilder {
    // Простейшая реализация синглетона.
    private static SudokuBuilder INSTANCE;

    /**
     * Поле, определяющее сложность (количество пустых клеток).
     */
    private int totalEmptyCells = 1000;

    /**
     * Поле, определяющее количество УЖЕ удаленных клеток.
     */
    private int emptyCellsCount = 0;

    public int getTotalEmptyCells() {
        return totalEmptyCells;
    }

    public int getEmptyCellsCount() {
        return emptyCellsCount;
    }

    /**
     * Решенная данная судоку.
     */
    private int[][] solution;

    private SudokuBuilder() {
    }

    public static SudokuBuilder getSudokuBuilder() {
        if (INSTANCE == null) {
            INSTANCE = new SudokuBuilder();
        }
        return INSTANCE;
    }

    // Сгенерированная задача судоку.
    private Sudoku generatedSudoku;

    public void setGeneratedSudoku(Sudoku sudoku) {
        this.generatedSudoku = sudoku;
    }

    /**
     * Генерация судоку с заданными параметрами.
     * @param boardSize размер судоку.
     * @param diffLevel уровень сложности судоку.
     */
    public void generateSudoku(int boardSize, DifficultyLevel diffLevel) {
        if (boardSize == 16) {
            totalEmptyCells = switch (diffLevel) {
                case EASY -> 100;
                case MEDIUM -> 120;
                case HARD -> 148;
            };
        } else {
            totalEmptyCells = switch (diffLevel) {
                case EASY -> 240;
                case MEDIUM -> 270;
                case HARD -> 300;
            };
        }
        int[][] sol = this.generateSolution(boardSize);
        generatedSudoku = new Sudoku(this.generateProblem(boardSize), sol, diffLevel);
    }

    /**
     * Получение сгенерированной судоку.
     * @return сгенерированная судоку.
     */
    public Sudoku getGeneratedSudoku() {
        return generatedSudoku;
    }

    /**
     * Метод позволяющий корректно(оставляя судоку однозначно решаемым) удалить некоторые клетки.
     * @param boardSize Размер доски.
     * @return Доска с некоторыми незаполненными клетками.
     */
    private int[][] generateProblem(int boardSize) {
        emptyCellsCount = 0;
        int[][] problem = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(solution[i], 0, problem[i], 0, boardSize);
        }

        record Coordinates(int x, int y) {
        }

        var pairs = new ArrayList<Coordinates>(boardSize * boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                pairs.add(new Coordinates(i, j));
            }
        }

        while (emptyCellsCount != totalEmptyCells + 1 && pairs.size() != 0) {
            int pairNum = ThreadLocalRandom.current().nextInt(0, pairs.size());
            int i = pairs.get(pairNum).x;
            int j = pairs.get(pairNum).y;
            int back = problem[i][j];
            problem[i][j] = -1;
            emptyCellsCount++;
            var algoStarter = new DLXAlgoStarter(problem);
            algoStarter.solve();
            if (!algoStarter.getIfOnlyOneSolution()) {
                problem[i][j] = back;
                emptyCellsCount--;
            }
            pairs.remove(pairNum);
        }

        return problem;
    }

    /**
     * Создание корректной полной судоку.
     * @param boardSize Размер доски.
     * @return Одно из всевозможных заполненных судоку (рандомный выбор).
     */
    private int[][] generateSolution(int boardSize) {
        return solution = new SudokuGrid(boardSize).getMixedGrid();
    }
}

package mega_sudoku.backend.sudoku;

import mega_sudoku.backend.dlx.DancingLinks;
import mega_sudoku.backend.dlx.DancingLinksAlgorithm;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс, отвечающий за генерацию судоку.
 */
public class SudokuBuilder {
    // Простейшая реализация синглетона.
    private static SudokuBuilder INSTANCE;

    /**
     * Поле определяющее сложность (количество пустых клеток).
     */
    private int emtyCellsCount = 1000;

    private int count = 0;

    public int getEmtyCellsCount() {
        return emtyCellsCount;
    }

    public int getCount() {
        return count;
    }

    private int[][] solution;

    private SudokuBuilder() { }

    public static SudokuBuilder getSudokuBuilder() {
        if (INSTANCE == null) {
            INSTANCE = new SudokuBuilder();
        }
        return INSTANCE;
    }

    // Сгенерированная судоку.
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
            emtyCellsCount = switch (diffLevel) {
                case EASY -> 100;
                case MEDIUM -> 120;
                case HARD -> 148;
            };
        } else {
            emtyCellsCount = switch (diffLevel) {
                case EASY -> 240;
                case MEDIUM -> 270;
                case HARD -> 300;
            };
        }
        int[][] sol = this.generateSolution(boardSize);
        int[][] pr = this.generateProblem(boardSize);
        generatedSudoku = new Sudoku(pr, sol, diffLevel);
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
     *
     * @param boardSize Размер доски.
     * @return Доска с некоторыми незаполненными клетками.
     */
    private int[][] generateProblem(int boardSize) {
        count = 0;
        int[][] problem = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(solution[i], 0, problem[i], 0, boardSize);
        }

        // Вспомогательная матрица (-1, если в основной матрице клетка заполненна; 1, если не заполненна).
        int[][] flook = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                flook[i][j] = -1;
            }
        }

        int iterator = 0;
        while (iterator < boardSize * boardSize) {
            if (count > emtyCellsCount) break;
            //System.out.println(count);
            int i = ThreadLocalRandom.current().nextInt(0, boardSize);
            int j = ThreadLocalRandom.current().nextInt(0, boardSize);
            if (flook[i][j] == -1) {
                iterator++;
                flook[i][j] = 1;
                int back = problem[i][j]; // Запоминаем цифру прежду чем удалить, на случай, если без нее решение будет не единственное.
                problem[i][j] = -1;
                count++;
                DancingLinksAlgorithm dancingLinksAlgorithm = new DancingLinksAlgorithm(problem);
                dancingLinksAlgorithm.solve();
                // Проверка единственности решения.
                if (!dancingLinksAlgorithm.getIfOnlyOneSolution()) {
                    problem[i][j] = back;
                    count--;
                }
                // Обнуляем счетчик решений.
                DancingLinks.zeroSolutions();
            }
        }
        return problem;
    }

    /**
     *
     * @param boardSize Размер доски.
     * @return Одно из всевозможных заполненных судоку (рандомный выбор).
     */
    private int[][] generateSolution(int boardSize) {
        SudokuGrid grid = new SudokuGrid(boardSize);
        return solution = grid.getMixedGrid(10);
    }
}

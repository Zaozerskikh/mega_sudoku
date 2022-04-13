package mega_sudoku.backend.game;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import mega_sudoku.backend.utils.ColorThemeManager;

/**
 * Класс, отвечающий за построение игровой сетки заданного размера.
 */
public class GameGridBuilder {
     //Холдер для объекта синглтона.
    private static GameGridBuilder INSTANCE;


    // Сгенерированная игровая сетка.
    private GridPane generatedPane;

    /**
     * Синглтон.
     * @return объект синглтона.
     */
    public static GameGridBuilder getBuilder() {
        if (INSTANCE == null) {
            INSTANCE = new GameGridBuilder();
        }
        return INSTANCE;
    }

    /**
     * Приватный конструктор для реализации синглтона.
     */
    private GameGridBuilder() {}

    /**
     * Создает сетку игры по заданной судоку.
     * @param boardSize размер доски.
     * @param currentPosition судоку.
     */
    @SuppressWarnings("all")
    public void buildGameGrid(int boardSize, int[][] currentPosition) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                TextField textField = new TextField();
                textField.setFocusTraversable(false);
                if (currentPosition[i][j] != -1) {
                    textField.setText(Integer.toString(currentPosition[i][j]));
                    textField.setEditable(false);
                }
                textField.setPrefSize((440 - 52) / boardSize, (440 - 52) / boardSize);
                textField.setFont(new Font((440 - 52) / (3 * boardSize) - 1));
                textField.setMaxHeight(Double.MAX_VALUE);
                textField.setMaxWidth(Double.MAX_VALUE);
                gridPane.add(textField, i, j);
            }
        }

        String cellBorderColor = (ColorThemeManager.isDarkTheme()) ? "#ffffff" : "#464646";
        String cellBackgroundColor = (ColorThemeManager.isDarkTheme()) ? "#464646" : "#ffffff";
        gridPane.getChildren().forEach(x -> {
            if (ColorThemeManager.isDarkTheme()) {
                x.setStyle("-fx-background-color: #525252; -fx-text-fill: white;");
            }
            if (GridPane.getRowIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 && GridPane.getRowIndex(x) != 0 &&
                    (GridPane.getColumnIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 && GridPane.getColumnIndex(x) != 0)) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 6 1 1 6; -fx-border-color: " + cellBorderColor);
            } else {
                if (GridPane.getRowIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 && GridPane.getRowIndex(x) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 6 1 1 1; -fx-border-color: " + cellBorderColor);
                }
                if (GridPane.getColumnIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 && GridPane.getColumnIndex(x) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 1 6; -fx-border-color: " + cellBorderColor);
                }
            }
        });
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 7 1 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == 0 && GridPane.getColumnIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 7 1 1 6; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 7 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getRowIndex(x) == boardSize - 1 && GridPane.getColumnIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 7 6; -fx-border-color: " + cellBorderColor);
            }

            if (GridPane.getColumnIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 1 7; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 6 1 1 7; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 7 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 6 7 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 1 7 7; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) == boardSize - 1) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 1 7 7 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == boardSize - 1 && GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 7 7 1 1; -fx-border-color: " + cellBorderColor);
            }
            if (GridPane.getColumnIndex(x) == 0 && GridPane.getRowIndex(x) == 0) {
                x.setStyle("-fx-text-fill: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor + "; -fx-border-width: 7 1 1 7; -fx-border-color: " + cellBorderColor);
            }
        });
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) != 0 && GridPane.getColumnIndex(x) != 0 &&
                    GridPane.getRowIndex(x) != boardSize - 1 && GridPane.getColumnIndex(x) != boardSize - 1 &&
                    GridPane.getRowIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) != 0 && GridPane.getColumnIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) != 0) {
                    x.setStyle("-fx-text-fill: " + cellBorderColor + " ; -fx-border-style: solid solid solid solid; -fx-border-width: 1; -fx-border-color: " + cellBorderColor + "; -fx-background-color: " + cellBackgroundColor);
            }
        });
        gridPane.getChildren().forEach(x -> {
            if (GridPane.getRowIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 || GridPane.getRowIndex(x) == boardSize - 1) {
                ((TextField)x).setPrefHeight((440 - 52) / boardSize + 5);
            }
            if (GridPane.getColumnIndex(x) % (int)(Math.ceil(Math.sqrt(boardSize))) == 0 || GridPane.getColumnIndex(x) == boardSize - 1) {
                ((TextField)x).setPrefWidth((440 - 52) / boardSize + 5);
            }
        });
        generatedPane = gridPane;
    }

    /**
     * Возвращает сгенерированное игровое поле.
     * @return сгенерированное игровое поле.
     */
    public GridPane getGeneratedPane() {
        return generatedPane;
    }
}

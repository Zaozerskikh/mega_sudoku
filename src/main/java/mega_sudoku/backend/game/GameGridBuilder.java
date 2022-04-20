package mega_sudoku.backend.game;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import mega_sudoku.backend.utils.ColorThemeManager;
import mega_sudoku.frontend.views.GameView;

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
                    textField.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, (440 - 52) / (3 * boardSize) - 1));
                } else {
                    textField.setFont(new Font((440 - 52) / (3 * boardSize) - 1));
                }
                textField.setPrefSize((440 - 52) / boardSize, (440 - 52) / boardSize);
                textField.setMaxHeight(Double.MAX_VALUE);
                textField.setMaxWidth(Double.MAX_VALUE);

                addEventHandlers(gridPane, textField);
                gridPane.add(textField, i, j);
            }
        }

        drawCellsBorder(boardSize, gridPane);
        generatedPane = gridPane;
    }

    // Добавление границ клеток игрового поля.
    @SuppressWarnings("all")
    private void drawCellsBorder(int boardSize, GridPane gridPane) {
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
    }

    // Добавление обработчиков событий.
    @SuppressWarnings("all")
    private void addEventHandlers(GridPane gridPane, TextField textField) {
        textField.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    try {
                        int target = Integer.parseInt(textField.getText());
                        gridPane.getChildren().forEach(x -> {
                            if (x.getClass() == TextField.class) {
                                if (((TextField)x).getText().equals(String.valueOf(target))) {
                                    ((TextField)x).setBackground(new Background(new BackgroundFill(Color.DARKORANGE, null, null)));
                                } else {
                                    if (ColorThemeManager.isDarkTheme()) {
                                        ((TextField)x).setBackground(new Background(new BackgroundFill(Color.valueOf("#464646"), null, null)));
                                    } else {
                                        ((TextField)x).setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                                    }
                                }
                            }
                        });
                    } catch (Exception e) { }
                } else {
                    GameView.resetCellsColor(gridPane);
                }
            }
        });
        textField.setOnKeyPressed(e -> GameView.resetCellsColor(gridPane));
    }

    /**
     * Возвращает сгенерированное игровое поле.
     * @return сгенерированное игровое поле.
     */
    public GridPane getGeneratedPane() {
        return generatedPane;
    }
}

package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.SudokuBuilder;
import com.example.mega_sudoku.backend.ToolBarManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

public class NewGameSettingsController {
    private int boardSize = 12;
    private int difficultyLevel = 1;

    @FXML
    protected Button startGameButton;

    @FXML
    protected CheckBox box12;

    @FXML
    protected CheckBox box16;

    @FXML
    protected Slider slider;

    @FXML
    protected Label easy;

    @FXML
    protected Label medium;

    @FXML
    protected Label hard;

    @FXML
    protected void selected16() {
        if (box12.isSelected()) {
            box12.setSelected(false);
            box16.setSelected(true);
            boardSize = 16;
        } else {
            box12.setSelected(true);
            box16.setSelected(false);
            boardSize = 12;
        }
    }

    @FXML
    protected void selected12() {
        if (box16.isSelected()) {
            box16.setSelected(false);
            box12.setSelected(true);
            boardSize = 12;
        } else {
            box16.setSelected(true);
            box12.setSelected(false);
            boardSize = 16;
        }
    }

    @FXML
    protected void sliderChange() {
        difficultyLevel = (int)slider.getValue();
        switch (difficultyLevel) {
            case 1 -> {
                easy.setFont(new Font(19));
                easy.setLayoutY(252);
                easy.setOpacity(1);
                medium.setFont(new Font(16));
                medium.setLayoutY(255);
                medium.setOpacity(0.6);
                hard.setFont(new Font(16));
                hard.setLayoutY(255);
                hard.setOpacity(0.6);
            }
            case 2 -> {
                medium.setFont(new Font(19));
                medium.setLayoutY(252);
                medium.setOpacity(1);
                easy.setFont(new Font(16));
                easy.setLayoutY(255);
                easy.setOpacity(0.6);
                hard.setOpacity(0.6);
                hard.setFont(new Font(16));
                hard.setLayoutY(255);
                hard.setOpacity(0.6);
            }
            case 3 -> {
                hard.setFont(new Font(19));
                hard.setLayoutY(252);
                hard.setOpacity(1);
                medium.setFont(new Font(16));
                medium.setLayoutY(255);
                medium.setOpacity(0.6);
                easy.setFont(new Font(16));
                easy.setLayoutY(255);
                easy.setOpacity(0.6);
            }
        }
    }

    @FXML
    protected void onNewGameButtonClick() throws IOException {
        ((Stage)startGameButton.getScene().getWindow()).close();
        GameController.createGame(new SudokuBuilder().generateSudoku(boardSize, difficultyLevel));
    }

    @FXML
    protected void onCloseButtonClick(ActionEvent e) throws IOException {
        ToolBarManager.onCloseButtonClick(e, (Stage)startGameButton.getScene().getWindow());
        HelloController.returnStartScreen();
    }

    @FXML
    protected void onMinimizeButtonClick(ActionEvent e) throws IOException {
        ToolBarManager.onMinimizeButtonClick(e, (Stage)startGameButton.getScene().getWindow());
    }

    @FXML
    protected void onMaximizeButtonClick(ActionEvent e) throws IOException {
        ToolBarManager.onMaximizeButtonClick(e, (Stage)startGameButton.getScene().getWindow());
    }

    @FXML
    protected void onMouseMoved(MouseEvent e) throws IOException {
        ToolBarManager.onMouseMoved(e, (Stage)startGameButton.getScene().getWindow());
    }

    @FXML
    protected void onMousePressed(MouseEvent e) throws IOException {
        ToolBarManager.onMousePressed(e, (Stage)startGameButton.getScene().getWindow());
    }

}

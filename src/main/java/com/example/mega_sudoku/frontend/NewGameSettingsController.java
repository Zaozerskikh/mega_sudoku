package com.example.mega_sudoku.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

public class NewGameSettingsController {
    private int boardSize = 16;
    private int difficultyLevel = 1;

    @FXML
    protected Button startGameButton;

    @FXML
    protected CheckBox fourBox;

    @FXML
    protected CheckBox fiveBox;

    @FXML
    protected Slider slider;

    @FXML
    protected Label easy;

    @FXML
    protected Label medium;

    @FXML
    protected Label hard;

    @FXML
    protected void fourSelected() {
        if (fourBox.isSelected()) {
            fiveBox.setSelected(false);
            boardSize = 16;
        } else {
            fiveBox.setSelected(true);
            boardSize = 25;
        }
    }

    @FXML
    protected void fiveSelected() {
        if (fiveBox.isSelected()) {
            fourBox.setSelected(false);
            boardSize = 25;
        } else {
            fourBox.setSelected(true);
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
        GameController.createGame(this.boardSize, this.difficultyLevel);
    }
}

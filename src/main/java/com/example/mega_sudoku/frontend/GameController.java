package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.Game;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class GameController {
    private static Game game;

    public static void createGame(int size, int diffLevel) throws IOException {
        game = new Game(size, diffLevel);
        Stage stage = game.generateGameStage();
        stage.show();
        stage.setOnCloseRequest(dialogEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите прервать игру и выйти?");
            alert.setTitle("Подтвердите действие");
            alert.showAndWait().ifPresent(response -> {
                if (response.getText().equals("OK")) {
                    stage.close();
                    HelloController.returnStartScreen();
                } else {
                    dialogEvent.consume();
                }
            });
        });
    }
}

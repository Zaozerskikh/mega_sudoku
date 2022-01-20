package com.example.mega_sudoku.frontend;
import com.example.mega_sudoku.backend.GameLoader;
import com.example.mega_sudoku.backend.Sudoku;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloController {
    @FXML
    protected Button devInfoButton, helpButton, myGamesButton, newGameButton;

    @FXML
    protected void onNewGameButtonClick(ActionEvent e) throws Exception {
        ((Stage)helpButton.getScene().getWindow()).close();
        showStage(getClass().getResource("/fxml_stages/new_game_settings_screen.fxml"), "Новая игра");
    }

    @FXML
    protected void onLoadSavedGameButtonClick(ActionEvent e) throws Exception {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku files (*.sudoku)", "*.sudoku"));
        var file = fc.showOpenDialog((Stage)helpButton.getScene().getWindow());
        if (file != null) {
            Sudoku sudoku = GameLoader.load(file);
            if (sudoku != null) {
                ((Stage)helpButton.getScene().getWindow()).close();
                GameController.createGame(sudoku);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "К сожалению, игру сохранить не удалось. Попробуйте еще раз.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    protected void onHelpButtonClick(ActionEvent e) throws Exception {
        showStage(getClass().getResource("/fxml_stages/help_screen.fxml"), "Помощь");
    }

    @FXML
    protected void onInfoButtonClick(ActionEvent e) throws IOException {
        showStage(getClass().getResource("/fxml_stages/dev_info_screen.fxml"), "О разработчиках");
    }

    private void showStage(URL resource, String stageName) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(stageName);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(resource)));
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage)helpButton.getScene().getWindow());
        stage.show();
        if (stageName.equals("Новая игра")) {
            stage.setResizable(false);
            stage.setOnCloseRequest(windowEvent -> returnStartScreen());
        }
    }

    public static void returnStartScreen() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(HelloController.class.getResource("/fxml_stages/hello_screen.fxml")))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Мега-Судоку");
        stage.setMinWidth(600);
        stage.setMinHeight(440);
        stage.getIcons().add(new Image("/icon.png"));
        stage.show();
        stage.setOnCloseRequest(dialogEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите выйти из игры?");
            alert.setTitle("Подтвердите действие");
            alert.showAndWait().ifPresent(response -> {
                if (response.getText().equals("OK")) {
                    stage.close();
                } else {
                    dialogEvent.consume();
                }
            });
        });
    }
}
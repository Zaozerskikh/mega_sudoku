package com.example.mega_sudoku.frontend;

import com.example.mega_sudoku.backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloController {
    @FXML
    protected Button devInfoButton, helpButton, myGamesButton, newGameButton, closeButton;

    @FXML
    protected void onNewGameButtonClick(ActionEvent e) throws Exception {
        ((Stage)helpButton.getScene().getWindow()).close();
        showStage(getClass().getResource("/fxml_stages/new_game_settings_screen.fxml"), "Новая игра",
                this.getClass().getResource("/styles/dark_settings_screen.css").toExternalForm(),
                this.getClass().getResource("/styles/white_settings_screen.css").toExternalForm());
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
        showStage(getClass().getResource("/fxml_stages/help_screen.fxml"), "Помощь",
                this.getClass().getResource("/styles/dark_info_screen.css").toExternalForm(),
                this.getClass().getResource("/styles/white_info_screen.css").toExternalForm());
    }

    @FXML
    protected void onInfoButtonClick(ActionEvent e) throws IOException {
        showStage(getClass().getResource("/fxml_stages/dev_info_screen.fxml"), "О разработчиках",
                this.getClass().getResource("/styles/dark_info_screen.css").toExternalForm(),
                this.getClass().getResource("/styles/white_info_screen.css").toExternalForm());
    }

    private void showStage(URL resource, String stageName, String darkPath, String whitePath) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(stageName);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(resource)));
        ColorThemeManager.setThemeToScene(scene, darkPath, whitePath);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner((Stage)helpButton.getScene().getWindow());
        if (stageName.equals("Новая игра")) {
            stage.setResizable(false);
        }
        stage.show();
    }

    public static void returnStartScreen() {
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(HelloController.class.getResource("/fxml_stages/start_screen.fxml"))));
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.getIcons().add(new Image("/icon.png"));
        ColorThemeManager.setThemeToScene(scene,
                HelloController.class.getResource("/styles/dark_start_screen.css").toExternalForm(),
                HelloController.class.getResource("/styles/white_start_screen.css").toExternalForm());
        stage.show();
    }

    public void onDarkThemeButtonClick(ActionEvent actionEvent) throws IOException {
        ColorThemeManager.switchTheme();
        ColorThemeManager.setThemeToScene(helpButton.getScene(),
                this.getClass().getResource("/styles/dark_start_screen.css").toExternalForm(),
                this.getClass().getResource("/styles/white_start_screen.css").toExternalForm());
    }

    public void onMinimizeButtonClick(ActionEvent actionEvent) {
        ToolBarManager.onMinimizeButtonClick(actionEvent, (Stage)helpButton.getScene().getWindow());
    }

    public void onMaximizeButtonClick(ActionEvent actionEvent) {
        ToolBarManager.onMaximizeButtonClick(actionEvent, (Stage)helpButton.getScene().getWindow());
    }

    public void onCloseButtonClick(ActionEvent actionEvent) throws IOException {
        Dialog dialog = new Dialog("confirm", "Подтвердите действие", "Выйти из программы?", (Stage)helpButton.getScene().getWindow());
        dialog.showDialog();
        dialog.yesButton.setOnAction(x -> ((Stage)helpButton.getScene().getWindow()).close());
    }


    public void onMousePressed(MouseEvent me) {
        ToolBarManager.onMousePressed(me, (Stage)helpButton.getScene().getWindow());
    }

    public void onMouseMoved(MouseEvent me) {
        ToolBarManager.onMouseMoved(me, (Stage)helpButton.getScene().getWindow());
    }
}
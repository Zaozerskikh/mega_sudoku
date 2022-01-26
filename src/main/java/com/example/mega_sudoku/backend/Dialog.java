package com.example.mega_sudoku.backend;

import com.example.mega_sudoku.frontend.StartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс представляющий кастомное диалоговое окно.
 */
public class Dialog {
    // Окно диалога.
    private final Stage dialogStage = new Stage();

    // Кнопка да.
    public Button yesButton;

    /**
     * Создание диалогового окна.
     * @param type тип диалога.
     * @param title заголовок диалога.
     * @param information информационное сообщение диалогового окна.
     * @param parent родительское окно.
     */
    public Dialog(String type, String title, String information, Stage parent) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(StartController.class.getResource("/fxml_stages/custom_dialog_screen.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialogStage.setScene(scene);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parent);
        if (!type.equals("confirm")) {
            ((Pane) scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(1).setVisible(false);
            ((Pane) scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(1).setDisable(true);
            ((Button) ((Pane) scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(2)).setText("Ок");
        }
        yesButton = ((Button)((Pane) scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(1));
        ((Label) ((GridPane) scene.getRoot().getChildrenUnmodifiable().get(0)).getChildren().get(2)).setText(title);
        ((Label) ((Pane) scene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(0)).setText(information);
        ColorThemeManager.setThemeToScene(scene,
                Objects.requireNonNull(StartController.class.getResource("/styles/dark_dialog_pane.css")).toExternalForm(),
                Objects.requireNonNull(StartController.class.getResource("/styles/white_dialog_pane.css")).toExternalForm());
    }

    /**
     * Показ диалогового окна.
     */
    public void showDialog() {
        dialogStage.show();
    }

    /**
     * Закрытие диалогового окна.
     */
    public void closeDialog() {
        dialogStage.close();
    }
}

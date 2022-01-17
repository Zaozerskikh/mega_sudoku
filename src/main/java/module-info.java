module com.example.mega_sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.mega_sudoku.frontend;
    opens com.example.mega_sudoku.frontend to javafx.fxml;
}
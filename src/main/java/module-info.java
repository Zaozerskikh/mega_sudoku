module com.example.mega_sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.mega_sudoku.frontend;
    opens com.example.mega_sudoku.frontend to javafx.fxml;
    exports com.example.mega_sudoku.backend;
    opens com.example.mega_sudoku.backend to javafx.fxml;
}
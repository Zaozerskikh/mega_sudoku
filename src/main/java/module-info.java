module com.example.mega_sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mega_sudoku to javafx.fxml;
    exports com.example.mega_sudoku;
}
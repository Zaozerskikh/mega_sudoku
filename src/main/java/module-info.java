module mega_sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    exports mega_sudoku.backend.dlx;
    opens mega_sudoku.backend.dlx to javafx.fxml;
    exports mega_sudoku.backend.utils;
    opens mega_sudoku.backend.utils to javafx.fxml;
    exports mega_sudoku.frontend.controllers;
    opens mega_sudoku.frontend.controllers to javafx.fxml;
    exports mega_sudoku.frontend.views;
    opens mega_sudoku.frontend.views to javafx.fxml;
    exports mega_sudoku.main;
    opens mega_sudoku.main to javafx.fxml;
    exports mega_sudoku.backend.sudoku;
    opens mega_sudoku.backend.sudoku to javafx.fxml;
    exports mega_sudoku.backend.models;
    opens mega_sudoku.backend.models to javafx.fxml;
    exports mega_sudoku.backend.game;
    opens mega_sudoku.backend.game to javafx.fxml;
}
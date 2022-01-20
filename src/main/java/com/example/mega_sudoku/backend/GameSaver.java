package com.example.mega_sudoku.backend;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GameSaver {
    public static void save(Sudoku sudoku, String key) throws Exception {
        FileOutputStream outputStream = new FileOutputStream("C://Users//Сергей//Desktop//savedGame.sudoku");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(sudoku);
        objectOutputStream.close();
    }
}

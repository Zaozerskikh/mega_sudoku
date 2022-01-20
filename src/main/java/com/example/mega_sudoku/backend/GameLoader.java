package com.example.mega_sudoku.backend;

import java.io.*;

public class GameLoader {
    public static Sudoku load(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Sudoku)objectInputStream.readObject();
        } catch (Exception ex) {
            return null;
        }
    }
}

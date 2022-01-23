package com.example.mega_sudoku.backend;

import java.io.*;

/**
 * Класс, отвечающий за загрузку сохранённой игры.
 */
public class GameLoader {

    /**
     * Загрузка судоку из файла.
     * @param file файл с сохранённой игрой.
     * @return загруженная судоку.
     */
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

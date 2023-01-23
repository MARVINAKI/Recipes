package com.example.recipes.services;

public interface FilesService {
    boolean writeToFile(String json);

    String readFromFile();

    void cleanDataFile();
}

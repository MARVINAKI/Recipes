package com.example.recipes.services;

public interface FilesService {
    void writeToFile(String json);

    String readFromFile();

    void cleanDataFile();
}

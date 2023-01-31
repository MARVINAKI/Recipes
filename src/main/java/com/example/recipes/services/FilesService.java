package com.example.recipes.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    void writeToFile(String json);

    String readFromFile();

    void cleanDataFile();

    Path createTempFile(String suffix);

    File getDataFile();
}

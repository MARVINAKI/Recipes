package com.example.recipes.services.Impl;

import com.example.recipes.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class RecipesFileServiceImpl
        implements FilesService {
    @Value("${pathToDataFile}")
    private String dataFilePath;
    @Value("${nameOfRecipeDataFile}")
    private String recDataFileName;

    @Override
    public void writeToFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, recDataFileName), json);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, recDataFileName));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void cleanDataFile() {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, recDataFileName));
            Files.createFile(Path.of(dataFilePath, recDataFileName));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}

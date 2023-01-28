package com.example.recipes.services.Impl;

import com.example.recipes.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class IngredientsFileServiceImpl
        implements FilesService {
    @Value("${pathToDataFile}")
    private String dataFilePath;
    @Value("${nameOfIngredientDataFile}")
    private String ingDataFileName;

    @Override
    public void writeToFile(String json) {
        try {
            Files.writeString(Path.of(dataFilePath, ingDataFileName), json);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, ingDataFileName));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void cleanDataFile() {
        try {
            Path path = Path.of(dataFilePath, ingDataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}

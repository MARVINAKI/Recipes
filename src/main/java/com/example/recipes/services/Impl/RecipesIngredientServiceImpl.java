package com.example.recipes.services.Impl;

import com.example.recipes.model.Ingredient;
import com.example.recipes.services.FilesService;
import com.example.recipes.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RecipesIngredientServiceImpl
        implements IngredientService {

    @Value("${pathToDataFile}")
    private String dataFilePath;
    @Value("${nameOfIngredientDataFile}")
    private String ingDataFileName;
    private final FilesService filesService;

    public RecipesIngredientServiceImpl(@Qualifier("ingredientsFileServiceImpl") FilesService filesService) {
        this.filesService = filesService;
    }


    private static Map<Integer, Ingredient> ingredients = new LinkedHashMap<>();

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (!ingredients.containsValue(ingredient)) {
            ingredients.put(ingredients.size() + 1, ingredient);
            writeToFile();
        }
    }

    @Override
    public Ingredient getIngredient(int id) {
        return ingredients.get(id);
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            writeToFile();
            return ingredient;
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int id) {
        Ingredient ingredient = ingredients.remove(id);
        return ingredient != null;
    }

    @PostConstruct
    private void maker() {
        checkOrFillMap();
        readFromFile();
    }

    private void writeToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.writeToFile(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            ingredients = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void checkOrFillMap() {
        if (!Files.exists(Path.of(dataFilePath, ingDataFileName))) {
            ingredients.put(0, new Ingredient());
            writeToFile();
        }
    }
}

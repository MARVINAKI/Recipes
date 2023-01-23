package com.example.recipes.services.Impl;

import com.example.recipes.model.Recipe;
import com.example.recipes.services.FilesService;
import com.example.recipes.services.IngredientService;
import com.example.recipes.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RecipeServiceImpl
        implements RecipeService {
    private final FilesService filesService;
    private Map<Integer, Recipe> recipes = new LinkedHashMap<>();
    private final IngredientService ingService;

    private static int id = 1;

    public RecipeServiceImpl(@Qualifier("recipesFileServiceImpl") FilesService filesService,
                             IngredientService ingService) {
        this.filesService = filesService;
        this.ingService = ingService;
    }

    @Override
    public final void addRecipe(Recipe recipe) {
        this.recipes.put(id++, recipe);
        writeToFile();
    }

    @Override
    public final Recipe getRecipe(int id) {
        return recipes.get(Math.abs(id));
    }

    @Override
    public final Collection<Recipe> getAllRecipes() {
        return recipes.values();
    }

    @Override
    public final Recipe editRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            writeToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public final boolean deleteRecipe(int id) {
        Recipe recipe = recipes.remove(id);
        return recipe != null;
    }

    @Override
    public Collection<Recipe> findRecByIng(int... id) {
        Set<Recipe> foundRecipes = new HashSet<>();
        for (Recipe recipe : recipes.values()) {
            for (int num : id) {
                if (recipe.getIngredients().contains(ingService.getIngredient(num))) {
                    foundRecipes.add(recipe);
                }
            }
        }
        return foundRecipes;
    }

    @PostConstruct
    public void maker() {
        readFromFile();
    }

    private void writeToFile() {
        try {
            //String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.writeToFile(new ObjectMapper().writeValueAsString(recipes));
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void readFromFile() {
        //String json = filesService.readFromFile();
        try {
            recipes = new ObjectMapper().readValue(filesService.readFromFile(), new TypeReference<LinkedHashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
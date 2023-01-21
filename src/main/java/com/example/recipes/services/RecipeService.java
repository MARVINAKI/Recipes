package com.example.recipes.services;

import com.example.recipes.model.Recipe;

import java.util.Collection;

public interface RecipeService {

    void addRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    Collection<Recipe> getAllRecipes();

    Collection<Recipe> findRecByIng(int... id);
}

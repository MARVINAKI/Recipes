package com.example.recipes.services.Impl;

import com.example.recipes.model.Recipe;
import com.example.recipes.services.IngredientService;
import com.example.recipes.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl
        implements RecipeService {
    private final Map<Integer, Recipe> recipes = new LinkedHashMap<>();
    private final IngredientService ingService;

    private static int id = 1;

    public RecipeServiceImpl(IngredientService ingService) {
        this.ingService = ingService;
    }

    @Override
    public final void addRecipe(Recipe recipe) {
        this.recipes.put(id++, recipe);
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
}
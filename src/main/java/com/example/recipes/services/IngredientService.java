package com.example.recipes.services;

import com.example.recipes.model.Ingredient;

import java.util.Collection;

public interface IngredientService {

    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int idOfIng);

    Ingredient editIngredient(int id, Ingredient ingredient);

    boolean deleteIngredient(int id);

    Collection<Ingredient> getAllIngredients();
}

package com.example.recipes.model;

import lombok.Data;

import java.util.*;

@Data
public class Recipe {
    private String title;
    private int timeOfCooking;
    private Set<Ingredient> ingredients = new LinkedHashSet<>();
    private Queue<String> steps = new ArrayDeque<>();

    public Recipe(String title, int timeOfCooking, Collection<Ingredient> ingredients, Queue<String> steps) {
        this.title = title == null || title.trim().isEmpty() ? null : title.toLowerCase();
        this.timeOfCooking = timeOfCooking;
        this.ingredients.addAll(ingredients);
        this.steps.addAll(steps);
    }
}

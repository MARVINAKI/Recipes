package com.example.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class Recipe {
    private String title;
    private int timeOfCooking;
    private Set<Ingredient> ingredients = new LinkedHashSet<>();
    private Queue<String> steps = new ArrayDeque<>();
}

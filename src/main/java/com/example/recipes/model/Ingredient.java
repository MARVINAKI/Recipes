package com.example.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ingredient {
    private String title;
    private double amount;
    private String unit;
}

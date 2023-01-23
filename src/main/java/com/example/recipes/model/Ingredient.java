package com.example.recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ingredient {
    private String title;
    private double amount;
    private String unit;

    public Ingredient(String title, double amount, String unit) {
        this.title = title == null || title.trim().isEmpty() ? null : title.toLowerCase();
        this.amount = Math.abs(amount);
        this.unit = unit == null || unit.trim().isEmpty() ? null : unit;
    }
}

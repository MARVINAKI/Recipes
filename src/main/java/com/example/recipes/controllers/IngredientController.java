package com.example.recipes.controllers;

import com.example.recipes.model.Ingredient;
import com.example.recipes.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingService) {
        this.ingredientService = ingService;
    }

    @PostMapping
    public Ingredient addIng(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ingredient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getRecipe(@PathVariable int id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        return ingredient == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(ingredient);
    }

    @GetMapping("/print")
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editRecipe(@PathVariable int id,
                                                 @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        return ingredient == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        return ingredientService.deleteIngredient(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

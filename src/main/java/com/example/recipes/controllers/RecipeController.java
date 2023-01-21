package com.example.recipes.controllers;

import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.services.IngredientService;
import com.example.recipes.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recService;
    private final IngredientService ingService;


    public RecipeController(RecipeService recService, IngredientService ingService) {
        this.recService = recService;
        this.ingService = ingService;
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        recService.addRecipe(recipe);
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingService.addIngredient(ingredient);
        }
        return recipe;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recService.getRecipe(id);
        return recipe == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(recipe);
    }

    @GetMapping("/find")
    public ResponseEntity<Collection<Recipe>> findRecByIng(@RequestParam int... id) {
        return ResponseEntity.ok(recService.findRecByIng(id));
    }

    @GetMapping("/print")
    public Collection<Recipe> getAllRecipes() {
        return recService.getAllRecipes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id,
                                             @RequestBody Recipe newRecipe) {
        Recipe recipe = recService.editRecipe(id, newRecipe);
        return recipe == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        return recService.deleteRecipe(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

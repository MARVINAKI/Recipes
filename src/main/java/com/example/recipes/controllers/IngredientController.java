package com.example.recipes.controllers;

import com.example.recipes.model.Ingredient;
import com.example.recipes.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@Tag(name = "Ингредиенты", description = "Создание и редактирование ингредиентов")
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingService) {
        this.ingredientService = ingService;
    }

    @PostMapping
    @Operation(summary = "Создание ингредиентов",
            description = "Создание одного ингредиента и добавление в базу данных")
    public Ingredient addIng(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ingredient;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск ингредиента по id",
            description = "Для вывода нужного ингредиента нужно знать его id")
    public ResponseEntity<Ingredient> getRecipe(@PathVariable int id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        return ingredient == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(ingredient);
    }

    @GetMapping("/print")
    @Operation(summary = "Поиск ингредиентов",
            description = "Выводит полный список ингредиентов")
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение ингредиента",
            description = "Возможность изменить частично или полностью ингредиент " +
                    "(выбор объекта по id, обязательно создаем полностью объект для замены)")
    public ResponseEntity<Ingredient> editRecipe(@PathVariable int id,
                                                 @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        return ingredient == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента",
            description = "Полное удаление ингредиента из базы (выбор объекта по id)")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        return ingredientService.deleteIngredient(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

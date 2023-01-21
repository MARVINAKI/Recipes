package com.example.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Страница приветсвия")
public class MainController {
    @GetMapping
    @Operation(summary = "Получить краткое описание")
    public String test() {
        return "Web-приложение для сайта рецептов";
    }
}

package com.example.recipes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping
    public String test() {
        return "Web-приложение для сайта рецептов";
    }
}

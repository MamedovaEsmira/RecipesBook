package com.example.recipesbook.controllers;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.services.impl.RecipeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/get")
    public Recipe getRecipe(@RequestParam int id){
        return recipeService.getRecipe(id);
    }

    @GetMapping("/add")
    public String addRecipe(@RequestParam String recipeName, @RequestParam int times, @RequestParam List<Ingredients> ingredientsList, @RequestParam List<String> steps) {
        recipeService.addRecipe(recipeName, times, ingredientsList, steps);
        return "Рецепт " +recipeName +" добавлен";
    }
}
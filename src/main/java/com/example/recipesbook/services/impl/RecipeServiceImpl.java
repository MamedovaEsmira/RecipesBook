package com.example.recipesbook.services.impl;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    Map<Integer, Recipe> recipeMap = new LinkedHashMap<>();
    public static int id = 0;

    @Override
    public void addRecipe(String recipeName, int times, List<Ingredients> ingredientsList, List<String> steps) {
        recipeMap.put(id++, new Recipe(recipeName, times, ingredientsList, steps));
    }

    @Override
    public Recipe getRecipe(int id) {
        if (recipeMap.containsKey(id) && id > 0) {
            return recipeMap.get(id);
        } else {
            throw new RuntimeException(" ID с данным рецептом не существует.");
        }
    }
}

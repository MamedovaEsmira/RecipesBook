package com.example.recipesbook.services;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.model.Recipe;
import java.util.List;
import java.util.Map;

public interface RecipeService {
    int addRecipe(Recipe recipe);

    void addRecipe(String recipeName, int times, List<Ingredients> ingredientsList, List<String> steps);

    Recipe getRecipe(int id);

    Map<Integer, Recipe> getAllRecipe();

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);
}

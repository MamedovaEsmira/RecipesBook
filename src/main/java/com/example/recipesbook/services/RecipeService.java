package com.example.recipesbook.services;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.model.Recipe;
import java.util.List;
public interface RecipeService {
    void addRecipe(String recipeName, int times, List<Ingredients> ingredientsList, List<String> steps);

    Recipe getRecipe(int id);
}

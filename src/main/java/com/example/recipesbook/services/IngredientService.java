package com.example.recipesbook.services;

import com.example.recipesbook.exception.IngredientNotFoundException;
import com.example.recipesbook.exception.RecipeNotFoundException;
import com.example.recipesbook.model.Ingredient;

import java.util.Map;

public interface IngredientService {

    int addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);

    Map<Integer, Ingredient> getAllIngredients();

    Ingredient editIngredient(int id, Ingredient ingredient);

    boolean deleteIngredient(int id);
}

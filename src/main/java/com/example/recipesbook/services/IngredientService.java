package com.example.recipesbook.services;

import com.example.recipesbook.model.Ingredients;

import java.util.Map;

public interface IngredientService {

    int addIngredient(Ingredients ingredients);

    Ingredients getIngredient(int id);

    Map<Integer, Ingredients> getAllIngredients();

    Ingredients editIngredient(int id, Ingredients ingredient);

    boolean deleteIngredient(int id);
}

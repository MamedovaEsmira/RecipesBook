package com.example.recipesbook.services;

import com.example.recipesbook.model.Ingredients;

public interface IngredientService {

    void addIngredient(String ingredientName, int sum, String units);

    Ingredients getIngredient(int id);
}


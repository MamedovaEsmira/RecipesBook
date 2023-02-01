package com.example.recipesbook.services;

import com.example.recipesbook.ExceptionsApp;
import com.example.recipesbook.model.Ingredients;

import java.util.Map;

public interface IngredientService {

    int addIngredient(Ingredients ingredients) throws ExceptionsApp;

    Ingredients getIngredient(int id) throws ExceptionsApp;

    Map<Integer, Ingredients> getAllIngredients() throws ExceptionsApp;

    Ingredients editIngredient(int id, Ingredients ingredient) throws ExceptionsApp;

    boolean deleteIngredient(int id) throws ExceptionsApp;
}

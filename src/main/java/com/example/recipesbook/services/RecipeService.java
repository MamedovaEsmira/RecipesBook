package com.example.recipesbook.services;

import com.example.recipesbook.ExceptionsApp;
import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface RecipeService {

    int addRecipe(Recipe recipe) throws ExceptionsApp;

    Recipe getRecipe(int id) throws ExceptionsApp;

    Map<Integer, Recipe> getAllRecipe() throws ExceptionsApp;

    Recipe editRecipe(int id, Recipe recipe) throws ExceptionsApp;

    boolean deleteRecipe(int id) throws ExceptionsApp;

    Path createRecipeText() throws IOException;
}
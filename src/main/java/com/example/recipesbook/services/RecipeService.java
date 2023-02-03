package com.example.recipesbook.services;

import com.example.recipesbook.NoFindException;
import com.example.recipesbook.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface RecipeService {

    int addRecipe(Recipe recipe) throws NoFindException;

    Recipe getRecipe(int id) throws NoFindException;

    Map<Integer, Recipe> getAllRecipe() throws NoFindException;

    Recipe editRecipe(int id, Recipe recipe) throws NoFindException;

    boolean deleteRecipe(int id) throws NoFindException;

    Path createRecipeText() throws IOException;
}
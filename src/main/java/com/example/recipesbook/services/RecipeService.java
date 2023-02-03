package com.example.recipesbook.services;
import com.example.recipesbook.model.Recipe;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface RecipeService {

    int addRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Map<Integer, Recipe> getAllRecipe();

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    Path createRecipeText() throws IOException;
}
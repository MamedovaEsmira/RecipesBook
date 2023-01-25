package com.example.recipesbook.services.impl;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.services.FileServiceRecipe;
import com.example.recipesbook.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final FileServiceRecipe fileServiceRecipe;
    private static Map<Integer, Recipe> recipeMap =new LinkedHashMap<>();
    public static int id = 0;

    public RecipeServiceImpl(FileServiceRecipe fileServiceRecipe) {
        this.fileServiceRecipe = fileServiceRecipe;
    }
@PostConstruct
private void init(){
        readFromFile();
}
    @Override
    public int addRecipe(Recipe recipe) {
        recipeMap.put(id++, recipe);
        saveToFile();
        return id;
    }

    @Override
    public void addRecipe(String recipeName, int times, List<Ingredients> ingredientsList, List<String> steps) {

    }

    @Override
    public Recipe getRecipe(int id) {
        if (recipeMap.containsKey(id) && id > 0) {
            return recipeMap.get(id);
        }
        return null;
    }

    @Override
    public Map<Integer, Recipe> getAllRecipe() {
        if (!recipeMap.isEmpty()) {
            return recipeMap;
        }
        return null;
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            return true;
        }
        return false;
    }
    private void saveToFile(){
        try {
         String json = new ObjectMapper().writeValueAsString(recipeMap);
         fileServiceRecipe.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile(){
        try {
            String json= fileServiceRecipe.readFromFile();
            recipeMap =  new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

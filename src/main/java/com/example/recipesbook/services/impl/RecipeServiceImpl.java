package com.example.recipesbook.services.impl;

import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.services.FilesService;
import com.example.recipesbook.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
@Service
public class RecipeServiceImpl implements RecipeService {
    private Map<Integer, Recipe> recipeMap = new TreeMap<>();
    public static int id = 0;

    private final FilesService filesService;

    @Value("${name.of.recipe.data.file}")
    private String recipeFileName;
    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }
    @PostConstruct
    private void init() throws IOException {
        readFromFile();
    }
    @Override
    public int addRecipe(Recipe recipe) {
        recipeMap.put(id++, recipe);
        saveToFile();
        return id;
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
            saveToFile();
            return true;
        }
        return false;
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFile(json, recipeFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Файл не найден");
        }
    }
    private void readFromFile() {
        String json = filesService.readFromFile(recipeFileName);
        try {
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
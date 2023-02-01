package com.example.recipesbook.services.impl;

import com.example.recipesbook.ExceptionsApp;
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
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addRecipe(Recipe recipe) throws ExceptionsApp {
        if(!recipeMap.containsValue(recipe)){
            recipeMap.put(id++, recipe);
            saveToFile();
            return id;
        } else{
            throw new ExceptionsApp("Такой рецепт уже существует.");
        }
    }

    @Override
    public Recipe getRecipe(int id) throws ExceptionsApp{
        if (recipeMap.containsKey(id) && id > 0) {
            return recipeMap.get(id);
        } else {
            throw new ExceptionsApp("Не найден файл с таким id.");
        }
    }

    @Override
    public Map<Integer, Recipe> getAllRecipe() throws ExceptionsApp{
        if (!recipeMap.isEmpty()) {
            return recipeMap;
        }else {
            throw new ExceptionsApp("Список рецептов пуст.");
        }
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) throws ExceptionsApp{
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            saveToFile();
            return recipe;
        }else {
            throw new ExceptionsApp("Не найден рецепт по id для редактирования.");
        }
    }

    @Override
    public boolean deleteRecipe(int id)throws ExceptionsApp {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            saveToFile();
            return true;
        }else {
            throw new ExceptionsApp("Удалить невозможно. Рецепт по id не найден.");
        }
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
    @Override
    public Path createRecipeText() throws IOException {
        Path path = filesService.createTempFile("Recipes");
        for (Recipe recipe : recipeMap.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getRecipeName())
                        .append(recipe.getTimes() + "\n")
                        .append(String.valueOf(recipe.getIngredientsList()))
                        .append("\n" + recipe.getSteps());
                writer.append("\n");
            }
        }
        return path;
    }
}
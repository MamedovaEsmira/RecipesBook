package com.example.recipesbook.services.impl;

import com.example.recipesbook.exception.RecipeAlreadyExistException;
import com.example.recipesbook.exception.RecipeNotFoundException;
import com.example.recipesbook.model.Ingredient;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
@Service
public class RecipeServiceImpl implements RecipeService {
    private Map<Integer, Recipe> recipeMap = new TreeMap<>();
    public  int id = 0;

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
    public int addRecipe(Recipe recipe){
        if (!recipeMap.containsValue(recipe)) {
            recipeMap.put(id++, recipe);
            saveToFile();
            return id;
        } else {
            throw new RecipeAlreadyExistException("Такой рецепт уже существует.");
        }
    }

    @Override
    public Recipe getRecipe(int id) {
        if (recipeMap.containsKey(id) && id > 0) {
            return recipeMap.get(id);
        } else {
            throw new RecipeNotFoundException("Не найден файл с таким id.");
        }
    }

    @Override
    public Map<Integer, Recipe> getAllRecipe(){
        if (!recipeMap.isEmpty()) {
            return recipeMap;
        } else {
            throw new RecipeNotFoundException("Список рецептов пуст.");
        }
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            saveToFile();
            return recipe;
        } else {
            throw new RecipeNotFoundException("Не найден рецепт по id для редактирования.");
        }
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            saveToFile();
            return true;
        } else {
            throw new RecipeNotFoundException("Удалить невозможно. Рецепт по id не найден.");
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
            throw new RuntimeException("Файл не найден");
        }
    }

    @Override
    public Path createRecipeText() throws IOException{
        recipeMap.getOrDefault(id, null);
        Path recipes = filesService.createTempFile("Recipes");
        try (Writer writer = Files.newBufferedWriter(recipes, StandardCharsets.UTF_8)) {
            for (Recipe recipe : recipeMap.values()) {
                StringBuilder ingredients = new StringBuilder();
                StringBuilder steps = new StringBuilder();
                for (Ingredient ingredient : recipe.getIngredientList()) {
                    ingredients.append(ingredient).append(", \r\n");
                }
                for (String instr : recipe.getSteps()) {
                    steps.append("\r\n").append(instr);
                }
                writer.append(recipe.getRecipeName()).append("\r\n").append("Время приготовления: \r\n")
                        .append(String.valueOf(recipe.getTimes())).append(" минут \r\n").append(" Необходимые ингредиенты: \r\n ")
                        .append(ingredients.toString()).append(" Инструкция: \r\n").append(steps.toString());
                writer.append("\r\n");
            }
        }
        return recipes;
    }
}
package com.example.recipesbook.services.impl;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.services.FilesService;
import com.example.recipesbook.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FilesService filesService;
    @Value("${name.of.ingredient.data.file}")
    private String ingredientFileName;
    public   Map<Integer, Ingredients> ingredientsMap = new TreeMap<>();
    public static int id = 0;

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }
    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public int addIngredient(Ingredients ingredients) {
        ingredientsMap.put(id++, ingredients);
        saveToFile();
        return id;
    }

    @Override
    public void addIngredient(String ingredientName, int sum, String units) {

    }

    @Override
    public Ingredients getIngredient(int id) {
        if (ingredientsMap.containsKey(id) && id > 0) {
            return ingredientsMap.get(id);
        }
        return null;
    }

    @Override
    public Map<Integer, Ingredients> getAllIngredients() {
        if (!ingredientsMap.isEmpty()) {
            return ingredientsMap;
        }
        return null;
    }

    @Override
    public Ingredients editIngredient(int id, Ingredients ingredient) {
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.put(id, ingredient);
            return ingredient;
        }
        saveToFile();
        return null;
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.remove(id);
            return true;
        }
        return false;
    }
    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientsMap);
            filesService.saveToFile(json,ingredientFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Файл не найден");
        }
    }
    private void readFromFile(){
        try {
            String json= filesService.readFromFile(ingredientFileName);
            ingredientsMap =  new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Ingredients>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
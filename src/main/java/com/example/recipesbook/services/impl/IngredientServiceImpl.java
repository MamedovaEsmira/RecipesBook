package com.example.recipesbook.services.impl;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.services.FileServiceIngredient;
import com.example.recipesbook.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FileServiceIngredient fileServiceIngredient;
    public   Map<Integer, Ingredients> ingredientsMap = new LinkedHashMap<>();
    public static int id = 0;

    public IngredientServiceImpl(FileServiceIngredient fileServiceIngredient) {
        this.fileServiceIngredient = fileServiceIngredient;
    }
    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public int addIngredient(Ingredients ingredients) {
        ingredientsMap.put(id++, ingredients);
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
            saveToFile();
            return ingredient;
        }
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
            fileServiceIngredient.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile(){
        try {
            String json= fileServiceIngredient.readFromFile();
            ingredientsMap =  new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Integer, Ingredients>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
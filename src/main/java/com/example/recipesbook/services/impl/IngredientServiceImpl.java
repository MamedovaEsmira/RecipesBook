package com.example.recipesbook.services.impl;

import com.example.recipesbook.exception.IngredientAlreadyExistException;
import com.example.recipesbook.model.Ingredient;
import com.example.recipesbook.services.FilesService;
import com.example.recipesbook.exception.IngredientNotFoundException;
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

    private Map<Integer, Ingredient> ingredientsMap = new TreeMap<>();
    public static int id = 0;

    private final FilesService filesService;

    @Value("${name.of.ingredient.data.file}")
    private String ingredientFileName;

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public int addIngredient(Ingredient ingredient) {
        if (!ingredientsMap.containsValue(ingredient)){
            ingredientsMap.put(id++, ingredient);
            saveToFile();
            return id;
        }
        else {
            throw new IngredientAlreadyExistException("Такой ингредиент уже есть.");
        }
    }

    @Override
    public Ingredient getIngredient(int id){
        if (ingredientsMap.containsKey(id) && id > 0) {
            return ingredientsMap.get(id);
        } else {
            throw new IngredientNotFoundException("Не найден ингредиент по id.");
        }
    }
    @Override
    public Map<Integer, Ingredient> getAllIngredients(){
        if (!ingredientsMap.isEmpty()) {
            return ingredientsMap;
        } else
            throw new IngredientNotFoundException("Список ингредиентов пуст.");
    }


    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.put(id, ingredient);
            saveToFile();
            return ingredient;
        } else
            throw new IngredientNotFoundException("Не найден ингредиент по id для редактирования.");
}

    @Override
    public boolean deleteIngredient(int id)  {
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.remove(id);
            saveToFile();
            return true;
        } else
            throw new IngredientNotFoundException("Не найден ингредиент по id для удаления.");
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientsMap);
            filesService.saveToFile(json, ingredientFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromFile(ingredientFileName);
            ingredientsMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
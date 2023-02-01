package com.example.recipesbook.services.impl;

import com.example.recipesbook.ExceptionsApp;
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

    private Map<Integer, Ingredients> ingredientsMap = new TreeMap<>();
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
    public int addIngredient(Ingredients ingredients) throws ExceptionsApp {
        if (!ingredientsMap.containsValue(ingredients)){
            ingredientsMap.put(id++, ingredients);
            saveToFile();
            return id;
        }
        else {
            throw new ExceptionsApp("Такой ингредиент уже есть.");
        }
    }

    @Override
    public Ingredients getIngredient(int id)throws ExceptionsApp {
        if (ingredientsMap.containsKey(id) && id > 0) {
            return ingredientsMap.get(id);
        } else {
            throw new ExceptionsApp("Не найден ингредиент по id.");
        }
    }
    @Override
    public Map<Integer, Ingredients> getAllIngredients()throws ExceptionsApp {
        if (!ingredientsMap.isEmpty()) {
            return ingredientsMap;
        } else
            throw new ExceptionsApp("Список ингредиентов пуст.");
    }


    @Override
    public Ingredients editIngredient(int id, Ingredients ingredient)throws ExceptionsApp {
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.put(id, ingredient);
            saveToFile();
            return ingredient;
        } else
            throw new ExceptionsApp("Не найден ингредиент по id для редактирования.");
}

    @Override
    public boolean deleteIngredient(int id) throws ExceptionsApp{
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.remove(id);
            saveToFile();
            return true;
        } else
            throw new ExceptionsApp("Не найден ингредиент по id для удаления.");
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientsMap);
            filesService.saveToFile(json, ingredientFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Файл не найден");
        }
    }
    private void readFromFile() {
        try {
            String json = filesService.readFromFile(ingredientFileName);
            ingredientsMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Ingredients>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
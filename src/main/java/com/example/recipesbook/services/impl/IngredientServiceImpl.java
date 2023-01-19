package com.example.recipesbook.services.impl;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.services.IngredientService;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    Map<Integer, Ingredients> ingredientsMap = new LinkedHashMap<>();
    public static int id = 0;

    @Override
    public void addIngredient(String ingredientName, int sum, String units) {
        ingredientsMap.put(id++, new Ingredients(ingredientName, sum, units));
    }
    @Override
    public Ingredients getIngredient(int id) {
        if (ingredientsMap.containsKey(id) && id > 0) {
            return ingredientsMap.get(id);
        } else {
            throw new RuntimeException(" ID с данным ингредиентом не существует.");
        }
    }
}
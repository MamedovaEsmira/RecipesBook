package com.example.recipesbook.services.impl;

import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.services.IngredientService;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

  public   Map<Integer, Ingredients> ingredientsMap = new LinkedHashMap<>();
    public static int id = 0;

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
}
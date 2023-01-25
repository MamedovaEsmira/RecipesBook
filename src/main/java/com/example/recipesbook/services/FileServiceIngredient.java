package com.example.recipesbook.services;

public interface FileServiceIngredient {
    boolean saveToFile(String json);

    String readFromFile();
}

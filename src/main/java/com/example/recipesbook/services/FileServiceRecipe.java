package com.example.recipesbook.services;

public interface FileServiceRecipe {
    boolean saveToFile(String json);

    String readFromFile();
}

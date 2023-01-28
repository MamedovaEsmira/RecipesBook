package com.example.recipesbook.services;

import java.io.File;

public interface FilesService {
    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);

    File getDataFileRecipe();

    File getDataFileIngredient();

    boolean cleanDataFile(String dataFileName);
}
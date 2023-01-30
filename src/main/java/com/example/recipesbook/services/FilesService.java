package com.example.recipesbook.services;

import java.io.File;

public interface FilesService {
    File getDataFile();

    File getDataFileRecipe();

    File getDataFileIngredient();

    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);

    boolean cleanDataFile(String dataFileName);
}
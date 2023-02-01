package com.example.recipesbook.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    File getDataFileRecipe();

    File getDataFileIngredient();

    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);

    boolean cleanDataFile(String dataFileName);

    Path createTempFile(String suffix);
}
package com.example.recipesbook.services;

public interface FilesService {
    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);

    boolean cleanDataFile(String dataFileName);
}
package com.example.recipesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
//@AllArgsConstructor
public class Recipe {
    private String recipeName;
    private int times;
    private List <Ingredients> ingredientsList;
    private List<String> steps;

}
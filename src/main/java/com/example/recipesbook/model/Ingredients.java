package com.example.recipesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Ingredients {
    private String ingredientName;
    private int sum;
    private String units;
}

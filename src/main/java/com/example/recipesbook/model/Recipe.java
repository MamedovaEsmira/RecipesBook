package com.example.recipesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;
@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Recipe {
    @NotBlank(message = "Имя пустое")
    private String recipeName;
    @Positive
    private int times;
    @NotEmpty
    private List <Ingredient> ingredientList;
    @NotEmpty
    private List<String> steps;

    @Override
    public String toString() {
        return  recipeName +" "+ times + " " + ingredientList + " " + steps;
    }
}
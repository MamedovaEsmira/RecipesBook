package com.example.recipesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Ingredients {
    @NotBlank(message = "Имя пустое")
    private String ingredientName;
    @Positive
    private int sum;
    private String units;

    @Override
    public String toString() {
        return  ingredientName + " " + sum + " " + units ;
    }
}

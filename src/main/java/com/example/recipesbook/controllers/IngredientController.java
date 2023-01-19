package com.example.recipesbook.controllers;
import com.example.recipesbook.model.Ingredients;
import com.example.recipesbook.services.impl.IngredientServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping("/get")
    public Ingredients getIngredient(@RequestParam int id){
        return ingredientService.getIngredient(id);
    }

    @GetMapping("/add")
    public String addIngredient(@RequestParam String ingredientName, @RequestParam int sum, @RequestParam String units){
        ingredientService.addIngredient(ingredientName, sum, units);
        return "Ингридиент " +ingredientName +" добавлен";
    }
}
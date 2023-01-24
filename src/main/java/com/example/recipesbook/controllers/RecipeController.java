package com.example.recipesbook.controllers;

import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.services.impl.RecipeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/recipe")
@Tag(name="Рецепты",description = "CRUD- операции и другие эндпоинты для работы с рецептами")
public class RecipeController {

    private  final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск рецепта по ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Рецепт найден!",
    content = {@Content(mediaType ="application/json",
    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Поиск всех рецептов")
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Рецепты найдены!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    public ResponseEntity<Map<Integer,Recipe>> getAllRecipes(){
        Map<Integer, Recipe> allRecipe = recipeService.getAllRecipe();
        if(allRecipe == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allRecipe);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление рецепта",
            description ="Добавление рецепта по его параметрам")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Рецепт  добавлен!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта по его ID",
            description ="Поиск и редактирование рецепта по его ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Рецепт редактирован!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.editRecipe(id, recipe);
        if (newRecipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта по ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Рецепт удален!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.example.recipesbook.controllers;

import com.example.recipesbook.ExceptionsApp;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


@RestController
@RequestMapping("/recipe")
@Tag(name="Рецепты",description = "CRUD- операции и другие эндпоинты для работы с рецептами")
public class RecipeController {

    private  final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск рецепта по ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Рецепт найден!",
    content = {@Content(mediaType ="application/json",
    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")})
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id)throws ExceptionsApp {
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
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")})
    public ResponseEntity<Map<Integer,Recipe>> getAllRecipes()throws ExceptionsApp {
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
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")})
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe)throws ExceptionsApp {
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта по его ID",
            description ="Поиск и редактирование рецепта по его ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Рецепт редактирован!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")})
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe)throws ExceptionsApp {
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
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")})
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id)throws ExceptionsApp {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping(value = "/txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Отчет по рецептам в формате .txt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Отчет по рецептам."),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")
    }
    )
    public ResponseEntity<Object> getRecipeText() {
        try {
            Path path = recipeService.createRecipeText();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
}

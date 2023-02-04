package com.example.recipesbook.controllers;

import com.example.recipesbook.exception.IngredientAlreadyExistException;
import com.example.recipesbook.exception.IngredientNotFoundException;
import com.example.recipesbook.model.Ingredient;
import com.example.recipesbook.services.IngredientService;
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
@RequestMapping("/ingredients")
@Tag(name="Ингредиенты",description = "CRUD- операции и другие эндпоинты для работы с ингредиентами")
public class IngredientController {

    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping("/{id}")
    @Operation(summary = "Поиск ингредиента по ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Ингредиент найден!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))}),
    @ApiResponse(responseCode = "400",
            description = "Ошибка запроса."),
    @ApiResponse(responseCode = "404",
            description = "Некорректный URL."),
    @ApiResponse(responseCode = "500",
            description = "Ошибка сервера.")
}
    )
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Поиск всех ингредиентов")
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Ингредиенты найдены!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))}),
    @ApiResponse(responseCode = "400",
            description = "Ошибка запроса."),
    @ApiResponse(responseCode = "404",
            description = "Некорректный URL."),
    @ApiResponse(responseCode = "500",
            description = "Ошибка сервера.")
}
    )
    public ResponseEntity<Map<Integer, Ingredient>> getAllIngredients()throws IngredientNotFoundException {
        Map<Integer, Ingredient> allIngredients = ingredientService.getAllIngredients();
        if(allIngredients == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allIngredients);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление ингредиента",
            description ="Добавление ингредиента по его параметрам")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Ингредиент  добавлен!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")
    }
    ) public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) throws IngredientAlreadyExistException {
        int id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok().body(id);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиента по его ID",
            description ="Поиск и редактирование ингредиента по его ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Ингредиент редактирован!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")
    })
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient)throws IngredientNotFoundException  {
        Ingredient editIngredient = ingredientService.editIngredient(id, ingredient);
        if (editIngredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editIngredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента по ID")
    @Parameters(value = { @Parameter(name="Id",example = "1")})
    @ApiResponses(value = { @ApiResponse(responseCode = "200",description = "Ингредиент удален!",
            content = {@Content(mediaType ="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера.")
    }
    )
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id)throws IngredientNotFoundException {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
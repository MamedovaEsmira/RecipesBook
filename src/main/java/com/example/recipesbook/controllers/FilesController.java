package com.example.recipesbook.controllers;

import com.example.recipesbook.services.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файловый контроллер.", description = "Для работы с файлами рецептов и ингредиентов.")
public class FilesController {
    @Value("${name.of.recipe.data.file}")
    private String dataFileNameRecipe;
    @Value("${name.of.ingredient.data.file}")
    private String dataFileNameIngredient;
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping(value = "/export/recipe")
    @Operation(summary = "Скачивание файла рецептов в JSON формате.")    public ResponseEntity<InputStreamResource> downloadDataFileRecipe() throws FileNotFoundException {
        File file = filesService.getDataFileRecipe();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeBookRecipe.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
        @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Импорт рецептов.", description = "Импорт файла рецептов в .json формате.")
        @ApiResponse(responseCode = "200", description = "Файл загружен.")
        public ResponseEntity<Void> uploadDataFileRecipe(@RequestParam MultipartFile file) {
            filesService.cleanDataFile(dataFileNameRecipe);
            File dataFile = filesService.getDataFileRecipe();
            try (FileOutputStream fos = new FileOutputStream(dataFile)) {
                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Импорт ингредиентов.", description = "Импорт файла ингредиентов в .json формате.")
        @ApiResponse(responseCode = "200", description = "Файл загружен.")
        public ResponseEntity<Void> uploadDataFileIngredient(@RequestParam MultipartFile file) {
            filesService.cleanDataFile(dataFileNameIngredient);
            File dataFile = filesService.getDataFileIngredient();
            try (FileOutputStream fos = new FileOutputStream(dataFile)) {
                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }

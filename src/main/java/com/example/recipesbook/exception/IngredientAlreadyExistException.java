package com.example.recipesbook.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IngredientAlreadyExistException extends RuntimeException {
    public IngredientAlreadyExistException(String s) {
        super(s);
    }
}
package com.example.recipesbook.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecipeAlreadyExistException extends RuntimeException{
    public RecipeAlreadyExistException(String message) {
        super(message);
    }
}

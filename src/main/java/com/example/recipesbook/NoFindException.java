package com.example.recipesbook;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoFindException extends Exception{
        public NoFindException(String s) {
        super(s);
    }
    }

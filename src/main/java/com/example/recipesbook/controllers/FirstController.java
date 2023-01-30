package com.example.recipesbook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class  FirstController {

    @GetMapping
    public String start(){
        return "Приложение запущено.";
    }

    @GetMapping("/info")
    public String info(){
        return "Автор: Мамедова Эсмира.\n"+
                "Название проекта: Recipes Book.\n" +
                "Дата создания проекта " + LocalDate.now()+ ".\n"+
                "Проект написан с использованием языка Java, Spring framework и сборщика Maven.";

    }
}

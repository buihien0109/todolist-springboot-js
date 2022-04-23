package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Random;

@SpringBootApplication
public class TodolistBackendApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TodolistBackendApplication.class, args);
    }

}

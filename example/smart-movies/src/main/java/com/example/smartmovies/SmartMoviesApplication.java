package com.example.smartmovies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.catalog.framework", "com.example.smartmovies"})
public class SmartMoviesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartMoviesApplication.class, args);
    }
}

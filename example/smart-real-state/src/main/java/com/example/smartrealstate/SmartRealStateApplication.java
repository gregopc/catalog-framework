package com.example.smartrealstate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.catalog.framework", "com.example.smartrealstate"})
public class SmartRealStateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartRealStateApplication.class, args);
    }
}

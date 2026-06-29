package com.example.smartstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.catalog.framework", "com.example.smartstore"})
public class SmartStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartStoreApplication.class, args);
    }
}

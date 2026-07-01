package com.example.smartstore.data;

import com.example.smartstore.domain.Product;
import com.example.smartstore.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public DataLoader(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        productRepository.deleteAll();

        try (InputStream inputStream = getClass().getResourceAsStream("/products.json")) {
            if (inputStream == null) {
                throw new RuntimeException("products.json not found!");
            }

            List<Product> products = Arrays.asList(
                    objectMapper.readValue(inputStream, Product[].class)
            );

            products.forEach(p -> {
                if (p.getStock() == null) {
                    System.out.println("Produto sem stock: " + p.getName());
                }
            });

            productRepository.saveAll(products);
        }
    }
}
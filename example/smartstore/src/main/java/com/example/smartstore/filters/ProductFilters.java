package com.example.smartstore.dto;

import java.math.BigDecimal;

public record ProductFilters(
        String category,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean inStock
) {
}
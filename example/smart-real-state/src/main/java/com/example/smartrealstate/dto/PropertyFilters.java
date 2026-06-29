package com.example.smartrealstate.dto;

import java.math.BigDecimal;

public record PropertyFilters(
        String type,
        String neighborhood,
        Integer bedrooms,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Double minArea
) {
}

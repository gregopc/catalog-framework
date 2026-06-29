package com.example.smartmovies.dto;

import java.math.BigDecimal;

public record MovieFilters(
        String genre,
        Integer year,
        Integer minYear,
        Integer maxYear,
        String platform,
        String director,
        BigDecimal minRating
) {
}

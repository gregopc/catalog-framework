package com.example.smartmovies.dto;

import com.catalog.framework.dto.EntryRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MovieRequest extends EntryRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String genre;

    private String cast;

    @NotBlank
    private String director;

    @NotNull
    @Min(1888)
    private Integer releaseYear;

    @NotBlank
    private String platform;

    private String synopsis;

    @DecimalMin("0.0")
    @Max(10)
    private BigDecimal rating;
}

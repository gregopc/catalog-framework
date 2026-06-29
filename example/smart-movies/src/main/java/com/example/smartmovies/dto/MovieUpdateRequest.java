package com.example.smartmovies.dto;

import com.catalog.framework.dto.EntryUpdateRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MovieUpdateRequest extends EntryUpdateRequest {

    private String title;
    private String genre;
    private String cast;
    private String director;

    @Min(1888)
    private Integer releaseYear;

    private String platform;
    private String synopsis;

    @DecimalMin("0.0")
    @Max(10)
    private BigDecimal rating;
}

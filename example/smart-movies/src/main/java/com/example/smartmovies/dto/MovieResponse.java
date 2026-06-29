package com.example.smartmovies.dto;

import com.catalog.framework.dto.EntryResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MovieResponse extends EntryResponse {

    private String title;
    private String genre;
    private String cast;
    private String director;
    private Integer releaseYear;
    private String platform;
    private String synopsis;
    private BigDecimal rating;
}

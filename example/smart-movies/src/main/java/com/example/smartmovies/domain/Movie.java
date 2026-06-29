package com.example.smartmovies.domain;

import com.catalog.framework.domain.Entry;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "movies")
public class Movie extends Entry {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(name = "movie_cast", columnDefinition = "text")
    private String cast; // Elenco

    @Column(nullable = false)
    private String director;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear; // Ano

    @Column(nullable = false)
    private String platform; // Plataforma de streaming

    @Column(columnDefinition = "text")
    private String synopsis; // Sinopse

    @Column(precision = 3, scale = 1)
    private BigDecimal rating; // Avaliações

    @Override
    public Map<String, Integer> getSearchableFields() {
        return Map.of(
                title != null ? title : "", 5,
                genre != null ? genre : "", 5,
                cast != null ? cast : "", 3,
                director != null ? director : "", 3,
                synopsis != null ? synopsis : "", 1
        );
    }

    @Override
    public String getDisplayName() {
        return title;
    }

    @Override
    public String getDisplaySummary() {
        return String.format(
                "Gênero: %s | Diretor: %s | Ano: %d | Plataforma: %s | Avaliação: %s",
                genre,
                director,
                releaseYear,
                platform,
                rating != null ? rating.toString() : "N/A"
        );
    }
}

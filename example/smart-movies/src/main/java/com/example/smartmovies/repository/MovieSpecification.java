package com.example.smartmovies.repository;

import com.catalog.framework.repository.EntrySpecification;
import com.example.smartmovies.domain.Movie;
import com.example.smartmovies.dto.MovieFilters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieSpecification
        extends EntrySpecification<Movie, MovieFilters> {

    @Override
    public Specification<Movie> withFilters(MovieFilters filters) {
        return Specification
                .where(equalIgnoreCase("genre", filters.genre()))
                .and(equalIgnoreCase("director", filters.director()))
                .and(equalIgnoreCase("platform", filters.platform()))
                .and(filters.year() != null ? (root, query, cb) -> cb.equal(root.get("releaseYear"), filters.year()) : null)
                .and(greaterThanOrEqual("releaseYear", filters.minYear()))
                .and(lessThanOrEqual("releaseYear", filters.maxYear()))
                .and(greaterThanOrEqual("rating", filters.minRating()));
    }
}

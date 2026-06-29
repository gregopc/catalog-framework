package com.example.smartmovies.service.impl;

import com.catalog.framework.mapper.EntryMapper;
import com.catalog.framework.repository.EntryRepository;
import com.catalog.framework.repository.EntrySpecification;
import com.catalog.framework.service.EntryServiceImpl;
import com.catalog.framework.domain.EntrySortField;
import com.example.smartmovies.domain.Movie;
import com.example.smartmovies.domain.MovieSortField;
import com.example.smartmovies.dto.MovieFilters;
import com.example.smartmovies.dto.MovieRequest;
import com.example.smartmovies.dto.MovieResponse;
import com.example.smartmovies.dto.MovieUpdateRequest;
import com.catalog.framework.exception.BusinessException;
import com.example.smartmovies.mapper.MovieMapper;
import com.example.smartmovies.repository.MovieRepository;
import com.example.smartmovies.repository.MovieSpecification;
import com.example.smartmovies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl extends EntryServiceImpl<
        Movie,
        MovieRequest,
        MovieUpdateRequest,
        MovieResponse,
        MovieFilters>
        implements MovieService {

    private final MovieRepository repository;
    private final MovieSpecification specification;
    private final MovieMapper mapper;

    @Override
    protected EntryRepository<Movie> getRepository() {
        return repository;
    }

    @Override
    protected EntrySpecification<Movie, MovieFilters> getSpecification() {
        return specification;
    }

    @Override
    protected EntryMapper<
            Movie,
            MovieRequest,
            MovieUpdateRequest,
            MovieResponse> getMapper() {
        return mapper;
    }

    @Override
    protected void validateOnSave(MovieRequest request) {
        if (request.getRating() != null && (request.getRating().compareTo(BigDecimal.ZERO) < 0 || request.getRating().compareTo(BigDecimal.TEN) > 0)) {
            throw new BusinessException("Rating must be between 0.0 and 10.0");
        }
        if (request.getReleaseYear() != null && request.getReleaseYear() < 1888) {
            throw new BusinessException("Release year must be 1888 or later");
        }
    }

    @Override
    protected void validateOnPartialUpdate(MovieUpdateRequest request) {
        if (request.getRating() != null && (request.getRating().compareTo(BigDecimal.ZERO) < 0 || request.getRating().compareTo(BigDecimal.TEN) > 0)) {
            throw new BusinessException("Rating must be between 0.0 and 10.0");
        }
        if (request.getReleaseYear() != null && request.getReleaseYear() < 1888) {
            throw new BusinessException("Release year must be 1888 or later");
        }
    }

    @Override
    protected Comparator<Movie> buildComparator(String sortBy) {
        EntrySortField.fromString(MovieSortField.class, sortBy);

        return switch (sortBy) {
            case "title" -> Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);
            case "rating" -> Comparator.comparing(Movie::getRating, Comparator.nullsLast(Comparator.naturalOrder()));
            case "releaseYear" -> Comparator.comparing(Movie::getReleaseYear);
            case "createdAt" -> Comparator.comparing(Movie::getCreatedAt);
            default -> Comparator.comparing(Movie::getCreatedAt);
        };
    }
}

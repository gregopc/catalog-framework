package com.example.smartmovies.mapper;

import com.catalog.framework.mapper.EntryMapper;
import com.example.smartmovies.domain.Movie;
import com.example.smartmovies.dto.MovieRequest;
import com.example.smartmovies.dto.MovieResponse;
import com.example.smartmovies.dto.MovieUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements
        EntryMapper<Movie, MovieRequest, MovieUpdateRequest, MovieResponse> {

    @Override
    public Movie toEntity(MovieRequest request) {
        if (request == null) {
            return null;
        }

        return Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .cast(request.getCast())
                .director(request.getDirector())
                .releaseYear(request.getReleaseYear())
                .platform(request.getPlatform())
                .synopsis(request.getSynopsis())
                .rating(request.getRating())
                .build();
    }

    @Override
    public MovieResponse toResponse(Movie entity) {
        if (entity == null) {
            return null;
        }

        MovieResponse response = MovieResponse.builder()
                .title(entity.getTitle())
                .genre(entity.getGenre())
                .cast(entity.getCast())
                .director(entity.getDirector())
                .releaseYear(entity.getReleaseYear())
                .platform(entity.getPlatform())
                .synopsis(entity.getSynopsis())
                .rating(entity.getRating())
                .build();

        mapBaseFields(entity, response);

        return response;
    }

    @Override
    public void updateEntity(Movie entity, MovieRequest request) {
        entity.setTitle(request.getTitle());
        entity.setGenre(request.getGenre());
        entity.setCast(request.getCast());
        entity.setDirector(request.getDirector());
        entity.setReleaseYear(request.getReleaseYear());
        entity.setPlatform(request.getPlatform());
        entity.setSynopsis(request.getSynopsis());
        entity.setRating(request.getRating());
    }

    @Override
    public void partialUpdateEntity(Movie entity, MovieUpdateRequest request) {
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getGenre() != null) {
            entity.setGenre(request.getGenre());
        }
        if (request.getCast() != null) {
            entity.setCast(request.getCast());
        }
        if (request.getDirector() != null) {
            entity.setDirector(request.getDirector());
        }
        if (request.getReleaseYear() != null) {
            entity.setReleaseYear(request.getReleaseYear());
        }
        if (request.getPlatform() != null) {
            entity.setPlatform(request.getPlatform());
        }
        if (request.getSynopsis() != null) {
            entity.setSynopsis(request.getSynopsis());
        }
        if (request.getRating() != null) {
            entity.setRating(request.getRating());
        }
    }
}

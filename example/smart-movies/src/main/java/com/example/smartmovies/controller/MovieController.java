package com.example.smartmovies.controller;

import com.catalog.framework.controller.EntryController;
import com.example.smartmovies.domain.Movie;
import com.example.smartmovies.domain.MovieSortField;
import com.example.smartmovies.dto.MovieFilters;
import com.example.smartmovies.dto.MovieRequest;
import com.example.smartmovies.dto.MovieResponse;
import com.example.smartmovies.dto.MovieUpdateRequest;
import com.example.smartmovies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovieController extends EntryController<
        Movie,
        MovieRequest,
        MovieUpdateRequest,
        MovieResponse,
        MovieFilters,
        MovieSortField> {

    private final MovieService movieService;

    @Override
    protected MovieService getService() {
        return movieService;
    }

    @Override
    protected Class<MovieSortField> getSortFieldClass() {
        return MovieSortField.class;
    }

    @Override
    protected MovieFilters buildFilters(Map<String, String[]> params) {
        return new MovieFilters(
                getParam(params, "genre"),
                getInteger(params, "year"),
                getInteger(params, "minYear"),
                getInteger(params, "maxYear"),
                getParam(params, "platform"),
                getParam(params, "director"),
                getBigDecimal(params, "minRating")
        );
    }

    @Override
    protected String getBasePath() {
        return "/movies";
    }
}

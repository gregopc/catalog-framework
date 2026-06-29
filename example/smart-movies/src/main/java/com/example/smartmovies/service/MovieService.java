package com.example.smartmovies.service;

import com.catalog.framework.service.EntryService;
import com.example.smartmovies.domain.Movie;
import com.example.smartmovies.dto.MovieFilters;
import com.example.smartmovies.dto.MovieRequest;
import com.example.smartmovies.dto.MovieResponse;
import com.example.smartmovies.dto.MovieUpdateRequest;

public interface MovieService extends EntryService<
        Movie,
        MovieRequest,
        MovieUpdateRequest,
        MovieResponse,
        MovieFilters> {
}

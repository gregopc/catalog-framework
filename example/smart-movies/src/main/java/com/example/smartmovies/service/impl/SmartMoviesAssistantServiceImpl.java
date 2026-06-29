package com.example.smartmovies.service.impl;

import com.catalog.framework.ai.AIClient;
import com.catalog.framework.service.EntryService;
import com.catalog.framework.service.AssistantServiceImpl;
import com.example.smartmovies.domain.Movie;
import com.example.smartmovies.dto.MovieFilters;
import com.example.smartmovies.dto.MovieRequest;
import com.example.smartmovies.dto.MovieResponse;
import com.example.smartmovies.dto.MovieUpdateRequest;
import com.example.smartmovies.mapper.MovieMapper;
import com.example.smartmovies.service.SmartMoviesAssistantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmartMoviesAssistantServiceImpl extends AssistantServiceImpl<
        Movie,
        MovieRequest,
        MovieUpdateRequest,
        MovieResponse,
        MovieFilters>
        implements SmartMoviesAssistantService {

    public SmartMoviesAssistantServiceImpl(
            EntryService<
                    Movie,
                    MovieRequest,
                    MovieUpdateRequest,
                    MovieResponse,
                    MovieFilters> movieService,
            MovieMapper movieMapper,
            AIClient aiClient) {

        super(movieService, movieMapper, aiClient);
    }
}

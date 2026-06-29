package com.example.smartmovies.domain;

import com.catalog.framework.domain.EntrySortField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MovieSortField implements EntrySortField {

    TITLE("title"),
    RATING("rating"),
    RELEASE_YEAR("releaseYear"),
    CREATED_AT("createdAt");

    private final String fieldName;
}

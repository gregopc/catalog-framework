package com.example.smartrealstate.domain;

import com.catalog.framework.domain.EntrySortField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PropertySortField implements EntrySortField {

    PRICE("price"),
    AREA("area"),
    BEDROOMS("bedrooms"),
    CREATED_AT("createdAt");

    private final String fieldName;
}

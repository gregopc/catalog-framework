package com.example.smartstore.domain;

import com.catalog.framework.domain.EntrySortField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSortField implements EntrySortField {

    PRICE("price"),
    NAME("name"),
    CREATED_AT("createdAt");

    private final String fieldName;
}
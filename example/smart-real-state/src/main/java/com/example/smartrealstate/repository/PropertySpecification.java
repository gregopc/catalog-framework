package com.example.smartrealstate.repository;

import com.catalog.framework.repository.EntrySpecification;
import com.example.smartrealstate.domain.Property;
import com.example.smartrealstate.dto.PropertyFilters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PropertySpecification
        extends EntrySpecification<Property, PropertyFilters> {

    @Override
    public Specification<Property> withFilters(PropertyFilters filters) {
        return Specification
                .where(equalIgnoreCase("type", filters.type()))
                .and(equalIgnoreCase("neighborhood", filters.neighborhood()))
                .and(filters.bedrooms() != null ? (root, query, cb) -> cb.equal(root.get("bedrooms"), filters.bedrooms()) : null)
                .and(greaterThanOrEqual("price", filters.minPrice()))
                .and(lessThanOrEqual("price", filters.maxPrice()))
                .and(greaterThanOrEqual("area", filters.minArea()));
    }
}

package com.example.smartstore.repository;

import com.catalog.framework.repository.EntrySpecification;
import com.example.smartstore.domain.Product;
import com.example.smartstore.dto.ProductFilters;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification
        extends EntrySpecification<Product, ProductFilters> {

    @Override
    public Specification<Product> withFilters(ProductFilters filters) {

        return Specification
                .where(equalIgnoreCase("category", filters.category()))
                .and(greaterThanOrEqual("price", filters.minPrice()))
                .and(lessThanOrEqual("price", filters.maxPrice()))
                .and(Boolean.TRUE.equals(filters.inStock())
                        ? greaterThan("stock", 0)
                        : null);
    }
}
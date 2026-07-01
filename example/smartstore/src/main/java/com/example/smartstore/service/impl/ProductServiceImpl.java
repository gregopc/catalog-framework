package com.example.smartstore.service.impl;

import com.catalog.framework.mapper.EntryMapper;
import com.catalog.framework.repository.EntryRepository;
import com.catalog.framework.repository.EntrySpecification;
import com.catalog.framework.service.EntryServiceImpl;
import com.catalog.framework.domain.EntrySortField;
import com.example.smartstore.domain.Product;
import com.example.smartstore.domain.ProductSortField;
import com.example.smartstore.dto.ProductFilters;
import com.example.smartstore.dto.ProductRequest;
import com.example.smartstore.dto.ProductResponse;
import com.example.smartstore.dto.ProductUpdateRequest;
import com.catalog.framework.exception.BusinessException;
import com.example.smartstore.mapper.ProductMapper;
import com.example.smartstore.repository.ProductRepository;
import com.example.smartstore.repository.ProductSpecification;
import com.example.smartstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends EntryServiceImpl<
        Product,
        ProductRequest,
        ProductUpdateRequest,
        ProductResponse,
        ProductFilters>
        implements ProductService {

    private final ProductRepository repository;
    private final ProductSpecification specification;
    private final ProductMapper mapper;

    @Override
    protected EntryRepository<Product> getRepository() {
        return repository;
    }

    @Override
    protected EntrySpecification<Product, ProductFilters> getSpecification() {
        return specification;
    }

    @Override
    protected EntryMapper<
            Product,
            ProductRequest,
            ProductUpdateRequest,
            ProductResponse> getMapper() {
        return mapper;
    }


    @Override
    protected Comparator<Product> buildComparator(String sortBy) {

        EntrySortField.fromString(ProductSortField.class, sortBy);

        return switch (sortBy) {
            case "price" ->
                    Comparator.comparing(Product::getPrice);

            case "name" ->
                    Comparator.comparing(
                            Product::getName,
                            String.CASE_INSENSITIVE_ORDER);

            case "createdAt" ->
                    Comparator.comparing(Product::getCreatedAt);

            default ->
                    throw new BusinessException("Invalid sort field: " + sortBy);
        };
    }
}
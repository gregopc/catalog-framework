package com.example.smartstore.service.impl;

import com.catalog.framework.mapper.EntryMapper;
import com.catalog.framework.repository.EntryRepository;
import com.catalog.framework.repository.EntrySpecification;
import com.catalog.framework.service.EntryServiceImpl;
import com.example.smartstore.domain.Product;
import com.example.smartstore.domain.ProductSortField;
import com.example.smartstore.dto.ProductFilters;
import com.example.smartstore.dto.ProductRequest;
import com.example.smartstore.dto.ProductResponse;
import com.example.smartstore.dto.ProductUpdateRequest;
import com.example.smartstore.exception.BusinessException;
import com.example.smartstore.mapper.ProductMapper;
import com.example.smartstore.repository.ProductRepository;
import com.example.smartstore.repository.ProductSpecification;
import com.example.smartstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            ProductResponse,
            ProductUpdateRequest> getMapper() {
        return mapper;
    }

    @Override
    protected void validateOnSave(ProductRequest request) {

        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Price must be positive");
        }

        if (request.getStock() < 0) {
            throw new BusinessException("Stock cannot be negative");
        }

        if (request.getImageUrl() != null && request.getImageUrl().isBlank()) {
            throw new BusinessException("Image URL cannot be blank");
        }
    }

    @Override
    protected void validateOnPartialUpdate(ProductUpdateRequest request) {

        if (request.getPrice() != null &&
                request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Price must be positive");
        }

        if (request.getStock() != null &&
                request.getStock() < 0) {
            throw new BusinessException("Stock cannot be negative");
        }

        if (request.getImageUrl() != null && request.getImageUrl().isBlank()) {
            throw new BusinessException("Image URL cannot be blank");
        }
    }

    @Override
    protected Comparator<Product> buildComparator(String sortBy) {

        ProductSortField.fromString(sortBy);

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
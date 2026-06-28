package com.example.smartstore.mapper;

import com.catalog.framework.mapper.EntryMapper;
import com.example.smartstore.domain.Product;
import com.example.smartstore.dto.ProductRequest;
import com.example.smartstore.dto.ProductResponse;
import com.example.smartstore.dto.ProductUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements
        EntryMapper<Product, ProductRequest, ProductUpdateRequest, ProductResponse> {

    @Override
    public Product toEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }

        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .stock(request.getStock())
                .build();
    }

    @Override
    public ProductResponse toResponse(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductResponse response = ProductResponse.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .category(entity.getCategory())
                .imageUrl(entity.getImageUrl())
                .stock(entity.getStock())
                .build();

        mapBaseFields(entity, response);

        return response;
    }

    @Override
    public void updateEntity(Product entity, ProductRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setCategory(request.getCategory());
        entity.setImageUrl(request.getImageUrl());
        entity.setStock(request.getStock());
    }

    @Override
    public void partialUpdateEntity(Product entity, ProductUpdateRequest request) {

        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            entity.setPrice(request.getPrice());
        }

        if (request.getCategory() != null) {
            entity.setCategory(request.getCategory());
        }

        if (request.getImageUrl() != null) {
            entity.setImageUrl(request.getImageUrl());
        }

        if (request.getStock() != null) {
            entity.setStock(request.getStock());
        }
    }
}
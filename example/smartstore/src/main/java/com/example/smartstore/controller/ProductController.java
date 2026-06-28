package com.example.smartstore.controller;

import com.catalog.framework.controller.EntryController;
import com.example.smartstore.domain.Product;
import com.example.smartstore.domain.ProductSortField;
import com.example.smartstore.dto.ProductFilters;
import com.example.smartstore.dto.ProductRequest;
import com.example.smartstore.dto.ProductResponse;
import com.example.smartstore.dto.ProductUpdateRequest;
import com.example.smartstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController extends EntryController<
        Product,
        ProductRequest,
        ProductUpdateRequest,
        ProductResponse,
        ProductFilters,
        ProductSortField> {

    private final ProductService productService;

    @Override
    protected ProductService getService() {
        return productService;
    }

    @Override
    protected Class<ProductSortField> getSortFieldClass() {
        return ProductSortField.class;
    }

    @Override
    protected ProductFilters buildFilters(Map<String, String[]> params) {
        return new ProductFilters(
                getParam(params, "category"),
                getBigDecimal(params, "minPrice"),
                getBigDecimal(params, "maxPrice"),
                getBoolean(params, "inStock")
        );
    }

    @Override
    protected String getBasePath() {
        return "/products";
    }
}
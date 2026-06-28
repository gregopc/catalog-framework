package com.example.smartstore.service;

import com.catalog.framework.service.EntryService;
import com.example.smartstore.domain.Product;
import com.example.smartstore.dto.ProductFilters;
import com.example.smartstore.dto.ProductRequest;
import com.example.smartstore.dto.ProductResponse;
import com.example.smartstore.dto.ProductUpdateRequest;

public interface ProductService extends EntryService<
        Product,
        ProductRequest,
        ProductUpdateRequest,
        ProductResponse,
        ProductFilters> {
}
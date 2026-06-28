package com.example.smartstore.service.impl;

import com.catalog.framework.ai.AIClient;
import com.catalog.framework.service.EntryService;
import com.catalog.framework.service.AssistantServiceImpl;
import com.example.smartstore.domain.Product;
import com.example.smartstore.dto.ProductFilters;
import com.example.smartstore.dto.ProductRequest;
import com.example.smartstore.dto.ProductResponse;
import com.example.smartstore.dto.ProductUpdateRequest;
import com.example.smartstore.mapper.ProductMapper;
import com.example.smartstore.service.SmartStoreAssistantService;
import lombok.extern.slf4j.Slf4j;
import main.java.com.example.smartstore.service.ProductService;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmartStoreAssistantServiceImpl extends AssistantServiceImpl<
        Product,
        ProductRequest,
        ProductUpdateRequest,
        ProductResponse,
        ProductFilters>
        implements SmartStoreAssistantService {

    public SmartStoreAssistantServiceImpl(
            EntryService<
                    Product,
                    ProductRequest,
                    ProductUpdateRequest,
                    ProductResponse,
                    ProductFilters> productService,
            ProductMapper productMapper,
            AIClient aiClient) {

        super(productService, productMapper, aiClient);
    }
}
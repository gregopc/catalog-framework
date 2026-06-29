package com.example.smartrealstate.controller;

import com.catalog.framework.controller.EntryController;
import com.example.smartrealstate.domain.Property;
import com.example.smartrealstate.domain.PropertySortField;
import com.example.smartrealstate.dto.PropertyFilters;
import com.example.smartrealstate.dto.PropertyRequest;
import com.example.smartrealstate.dto.PropertyResponse;
import com.example.smartrealstate.dto.PropertyUpdateRequest;
import com.example.smartrealstate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PropertyController extends EntryController<
        Property,
        PropertyRequest,
        PropertyUpdateRequest,
        PropertyResponse,
        PropertyFilters,
        PropertySortField> {

    private final PropertyService propertyService;

    @Override
    protected PropertyService getService() {
        return propertyService;
    }

    @Override
    protected Class<PropertySortField> getSortFieldClass() {
        return PropertySortField.class;
    }

    @Override
    protected PropertyFilters buildFilters(Map<String, String[]> params) {
        return new PropertyFilters(
                getParam(params, "type"),
                getParam(params, "neighborhood"),
                getInteger(params, "bedrooms"),
                getBigDecimal(params, "minPrice"),
                getBigDecimal(params, "maxPrice"),
                getDouble(params, "minArea")
        );
    }

    @Override
    protected String getBasePath() {
        return "/properties";
    }

    private Double getDouble(Map<String, String[]> params, String name) {
        String value = getParam(params, name);
        return value == null ? null : Double.valueOf(value);
    }
}

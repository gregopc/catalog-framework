package com.example.smartrealstate.service;

import com.catalog.framework.service.EntryService;
import com.example.smartrealstate.domain.Property;
import com.example.smartrealstate.dto.PropertyFilters;
import com.example.smartrealstate.dto.PropertyRequest;
import com.example.smartrealstate.dto.PropertyResponse;
import com.example.smartrealstate.dto.PropertyUpdateRequest;

public interface PropertyService extends EntryService<
        Property,
        PropertyRequest,
        PropertyUpdateRequest,
        PropertyResponse,
        PropertyFilters> {
}

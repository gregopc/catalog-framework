package com.example.smartrealstate.service.impl;

import com.catalog.framework.ai.AIClient;
import com.catalog.framework.service.EntryService;
import com.catalog.framework.service.AssistantServiceImpl;
import com.example.smartrealstate.domain.Property;
import com.example.smartrealstate.dto.PropertyFilters;
import com.example.smartrealstate.dto.PropertyRequest;
import com.example.smartrealstate.dto.PropertyResponse;
import com.example.smartrealstate.dto.PropertyUpdateRequest;
import com.example.smartrealstate.mapper.PropertyMapper;
import com.example.smartrealstate.service.SmartRealStateAssistantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmartRealStateAssistantServiceImpl extends AssistantServiceImpl<
        Property,
        PropertyRequest,
        PropertyUpdateRequest,
        PropertyResponse,
        PropertyFilters>
        implements SmartRealStateAssistantService {

    public SmartRealStateAssistantServiceImpl(
            EntryService<
                    Property,
                    PropertyRequest,
                    PropertyUpdateRequest,
                    PropertyResponse,
                    PropertyFilters> propertyService,
            PropertyMapper propertyMapper,
            AIClient aiClient) {

        super(propertyService, propertyMapper, aiClient);
    }
}

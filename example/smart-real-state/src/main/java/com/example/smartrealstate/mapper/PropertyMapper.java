package com.example.smartrealstate.mapper;

import com.catalog.framework.mapper.EntryMapper;
import com.example.smartrealstate.domain.Property;
import com.example.smartrealstate.dto.PropertyRequest;
import com.example.smartrealstate.dto.PropertyResponse;
import com.example.smartrealstate.dto.PropertyUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper implements
        EntryMapper<Property, PropertyRequest, PropertyUpdateRequest, PropertyResponse> {

    @Override
    public Property toEntity(PropertyRequest request) {
        if (request == null) {
            return null;
        }

        return Property.builder()
                .type(request.getType())
                .neighborhood(request.getNeighborhood())
                .area(request.getArea())
                .bedrooms(request.getBedrooms())
                .price(request.getPrice())
                .condoFee(request.getCondoFee())
                .description(request.getDescription())
                .additionalFeatures(request.getAdditionalFeatures())
                .build();
    }

    @Override
    public PropertyResponse toResponse(Property entity) {
        if (entity == null) {
            return null;
        }

        PropertyResponse response = PropertyResponse.builder()
                .type(entity.getType())
                .neighborhood(entity.getNeighborhood())
                .area(entity.getArea())
                .bedrooms(entity.getBedrooms())
                .price(entity.getPrice())
                .condoFee(entity.getCondoFee())
                .description(entity.getDescription())
                .additionalFeatures(entity.getAdditionalFeatures())
                .build();

        mapBaseFields(entity, response);

        return response;
    }

    @Override
    public void updateEntity(Property entity, PropertyRequest request) {
        entity.setType(request.getType());
        entity.setNeighborhood(request.getNeighborhood());
        entity.setArea(request.getArea());
        entity.setBedrooms(request.getBedrooms());
        entity.setPrice(request.getPrice());
        entity.setCondoFee(request.getCondoFee());
        entity.setDescription(request.getDescription());
        entity.setAdditionalFeatures(request.getAdditionalFeatures());
    }

    @Override
    public void partialUpdateEntity(Property entity, PropertyUpdateRequest request) {
        if (request.getType() != null) {
            entity.setType(request.getType());
        }
        if (request.getNeighborhood() != null) {
            entity.setNeighborhood(request.getNeighborhood());
        }
        if (request.getArea() != null) {
            entity.setArea(request.getArea());
        }
        if (request.getBedrooms() != null) {
            entity.setBedrooms(request.getBedrooms());
        }
        if (request.getPrice() != null) {
            entity.setPrice(request.getPrice());
        }
        if (request.getCondoFee() != null) {
            entity.setCondoFee(request.getCondoFee());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getAdditionalFeatures() != null) {
            entity.setAdditionalFeatures(request.getAdditionalFeatures());
        }
    }
}

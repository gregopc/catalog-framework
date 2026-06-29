package com.example.smartrealstate.service.impl;

import com.catalog.framework.mapper.EntryMapper;
import com.catalog.framework.repository.EntryRepository;
import com.catalog.framework.repository.EntrySpecification;
import com.catalog.framework.service.EntryServiceImpl;
import com.catalog.framework.domain.EntrySortField;
import com.example.smartrealstate.domain.Property;
import com.example.smartrealstate.domain.PropertySortField;
import com.example.smartrealstate.dto.PropertyFilters;
import com.example.smartrealstate.dto.PropertyRequest;
import com.example.smartrealstate.dto.PropertyResponse;
import com.example.smartrealstate.dto.PropertyUpdateRequest;
import com.catalog.framework.exception.BusinessException;
import com.example.smartrealstate.mapper.PropertyMapper;
import com.example.smartrealstate.repository.PropertyRepository;
import com.example.smartrealstate.repository.PropertySpecification;
import com.example.smartrealstate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl extends EntryServiceImpl<
        Property,
        PropertyRequest,
        PropertyUpdateRequest,
        PropertyResponse,
        PropertyFilters>
        implements PropertyService {

    private final PropertyRepository repository;
    private final PropertySpecification specification;
    private final PropertyMapper mapper;

    @Override
    protected EntryRepository<Property> getRepository() {
        return repository;
    }

    @Override
    protected EntrySpecification<Property, PropertyFilters> getSpecification() {
        return specification;
    }

    @Override
    protected EntryMapper<
            Property,
            PropertyRequest,
            PropertyUpdateRequest,
            PropertyResponse> getMapper() {
        return mapper;
    }

    @Override
    protected void validateOnSave(PropertyRequest request) {
        if (request.getPrice() != null && request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Price cannot be negative");
        }
        if (request.getArea() != null && request.getArea() <= 0) {
            throw new BusinessException("Area must be positive");
        }
    }

    @Override
    protected void validateOnPartialUpdate(PropertyUpdateRequest request) {
        if (request.getPrice() != null && request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Price cannot be negative");
        }
        if (request.getArea() != null && request.getArea() <= 0) {
            throw new BusinessException("Area must be positive");
        }
    }

    @Override
    protected Comparator<Property> buildComparator(String sortBy) {
        EntrySortField.fromString(PropertySortField.class, sortBy);

        return switch (sortBy) {
            case "price" -> Comparator.comparing(Property::getPrice);
            case "area" -> Comparator.comparing(Property::getArea);
            case "bedrooms" -> Comparator.comparing(Property::getBedrooms);
            case "createdAt" -> Comparator.comparing(Property::getCreatedAt);
            default -> Comparator.comparing(Property::getCreatedAt);
        };
    }
}

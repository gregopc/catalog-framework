package com.example.smartrealstate.dto;

import com.catalog.framework.dto.EntryUpdateRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PropertyUpdateRequest extends EntryUpdateRequest {

    private String type;

    private String neighborhood;

    @Positive
    private Double area;

    @Min(0)
    private Integer bedrooms;

    @DecimalMin("0.0")
    private BigDecimal price;

    @DecimalMin("0.0")
    private BigDecimal condoFee;

    private String description;

    private String additionalFeatures;
}

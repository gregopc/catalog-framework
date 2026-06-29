package com.example.smartrealstate.dto;

import com.catalog.framework.dto.EntryResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PropertyResponse extends EntryResponse {

    private String type;
    private String neighborhood;
    private Double area;
    private Integer bedrooms;
    private BigDecimal price;
    private BigDecimal condoFee;
    private String description;
    private String additionalFeatures;
}

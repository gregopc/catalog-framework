package com.example.smartrealstate.dto;

import com.catalog.framework.dto.EntryRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PropertyRequest extends EntryRequest {

    @NotBlank
    private String type;

    @NotBlank
    private String neighborhood;

    @NotNull
    @Positive
    private Double area;

    @NotNull
    @Min(0)
    private Integer bedrooms;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @DecimalMin("0.0")
    private BigDecimal condoFee;

    private String description;

    private String additionalFeatures;
}

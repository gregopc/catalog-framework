package com.example.smartstore.dto;

import com.catalog.framework.dto.EntryUpdateRequest;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductUpdateRequest extends EntryUpdateRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private String category;

    private String imageUrl;

    @Min(0)
    private Integer stock;
}
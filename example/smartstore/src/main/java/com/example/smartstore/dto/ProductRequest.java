package com.example.smartstore.dto;

import com.catalog.framework.dto.EntryRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductRequest extends EntryRequest {

    @NotBlank
    @Size(min = 3)
    private String name;

    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private String category;

    private String imageUrl;

    @NotNull
    @Min(0)
    private Integer stock;
}
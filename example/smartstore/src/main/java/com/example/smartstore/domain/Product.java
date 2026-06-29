package com.example.smartstore.domain;

import com.catalog.framework.domain.Entry;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "products")
public class Product extends Entry {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Integer stock;

    @Override
    public Map<String, Integer> getSearchableFields() {
        return Map.of(
                name, 3,
                category != null ? category : "", 2,
                description != null ? description : "", 1
        );
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDisplaySummary() {
        return String.format(
                "Categoria: %s | Preço: R$ %s | Estoque: %d",
                category,
                price,
                stock
        );
    }
}
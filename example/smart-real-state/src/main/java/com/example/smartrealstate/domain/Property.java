package com.example.smartrealstate.domain;

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
@Table(name = "properties")
public class Property extends Entry {

    @Column(name = "property_type", nullable = false)
    private String type; // Tipo do imóvel (ex: Casa, Apartamento)

    @Column(nullable = false)
    private String neighborhood; // Bairro

    @Column(nullable = false)
    private Double area; // Área em m²

    @Column(nullable = false)
    private Integer bedrooms; // Quantidade de quartos

    @Column(nullable = false)
    private BigDecimal price; // Valor

    @Column(name = "condo_fee")
    private BigDecimal condoFee; // Valor do condomínio

    @Column(columnDefinition = "text")
    private String description; // Descrição

    @Column(name = "additional_features", columnDefinition = "text")
    private String additionalFeatures; // Características adicionais (ex: Piscina, Churrasqueira)

    @Override
    public Map<String, Integer> getSearchableFields() {
        return Map.of(
                neighborhood != null ? neighborhood : "", 5,
                type != null ? type : "", 5,
                description != null ? description : "", 3,
                additionalFeatures != null ? additionalFeatures : "", 1
        );
    }

    @Override
    public String getDisplayName() {
        return String.format("%s no %s", type, neighborhood);
    }

    @Override
    public String getDisplaySummary() {
        return String.format(
                "Tipo: %s | Bairro: %s | Área: %.1fm² | Quartos: %d | Valor: R$ %s | Condomínio: R$ %s",
                type,
                neighborhood,
                area,
                bedrooms,
                price.toString(),
                condoFee != null ? condoFee.toString() : "N/A"
        );
    }
}

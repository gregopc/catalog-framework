package com.catalog.framework.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Hot spot: define quais campos entram no scoring e seus pesos.
     * Exemplo: Map.of(getName(), 3, getCategory(), 2, getDescription(), 1)
     */
    public abstract Map<String, Integer> getSearchableFields();

    /**
     * Hot spot: nome principal da entrada, usado nos prompts do assistente IA.
     */
    public abstract String getDisplayName();

    /**
     * Hot spot: resumo da entrada para o contexto do assistente IA.
     */
    public abstract String getDisplaySummary();
}

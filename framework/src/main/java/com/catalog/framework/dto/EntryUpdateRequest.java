package com.catalog.framework.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Hot spot: subclasses definem os campos opcionais para atualização parcial.
 * Todos os campos devem ser nullable — apenas os não-nulos são aplicados no merge.
 * Usado no endpoint PATCH (atualização parcial).
 */
@NoArgsConstructor
@SuperBuilder
public abstract class EntryUpdateRequest {
}

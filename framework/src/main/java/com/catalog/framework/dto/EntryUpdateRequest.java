package com.catalog.framework.dto;

/**
 * Hot spot: subclasses definem os campos opcionais para atualização parcial.
 * Todos os campos devem ser nullable — apenas os não-nulos são aplicados no merge.
 * Usado no endpoint PATCH (atualização parcial).
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class EntryUpdateRequest {
}

package com.catalog.framework.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Hot spot: subclasses definem os campos obrigatórios e validações do domínio.
 * Usado nos endpoints POST (criar) e PUT (atualização completa).
 */
@NoArgsConstructor
@SuperBuilder
public abstract class EntryRequest {
}

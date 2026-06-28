package com.catalog.framework.repository;

import com.catalog.framework.domain.Entry;
import org.springframework.data.jpa.domain.Specification;

public abstract class EntrySpecification<E extends Entry, F> {

    /**
     * Hot spot: subclasses definem os critérios de filtro para o domínio.
     */
    public abstract Specification<E> withFilters(F filters);

    // Helpers frozen — padrões comuns reutilizáveis nas subclasses

    protected Specification<E> equalIgnoreCase(String field, String value) {
        return (root, query, cb) -> value == null ? null
                : cb.equal(cb.lower(root.get(field)), value.toLowerCase());
    }

    protected <Y extends Comparable<? super Y>> Specification<E> greaterThanOrEqual(String field, Y value) {
        return (root, query, cb) -> value == null ? null
                : cb.greaterThanOrEqualTo(root.get(field), value);
    }

    protected <Y extends Comparable<? super Y>> Specification<E> lessThanOrEqual(String field, Y value) {
        return (root, query, cb) -> value == null ? null
                : cb.lessThanOrEqualTo(root.get(field), value);
    }

    protected Specification<E> greaterThan(String field, int value) {
        return (root, query, cb) -> cb.greaterThan(root.get(field), value);
    }
}

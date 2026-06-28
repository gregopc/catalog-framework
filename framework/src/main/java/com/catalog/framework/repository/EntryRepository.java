package com.catalog.framework.repository;

import com.catalog.framework.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EntryRepository<E extends Entry>
        extends JpaRepository<E, UUID>, JpaSpecificationExecutor<E> {
}

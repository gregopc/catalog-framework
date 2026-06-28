package com.catalog.framework.service;

import com.catalog.framework.domain.Entry;
import com.catalog.framework.dto.EntryRequest;
import com.catalog.framework.dto.EntryResponse;
import com.catalog.framework.dto.EntryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EntryService<
        E extends Entry,
        REQ extends EntryRequest,
        UPD extends EntryUpdateRequest,
        RES extends EntryResponse,
        F> {

    RES create(REQ request);

    Page<RES> list(F filters, Pageable pageable);

    RES getById(UUID id);

    Page<RES> search(String query, F filters, String sortBy, String sortDir, Pageable pageable);

    RES update(UUID id, REQ request);

    RES partialUpdate(UUID id, UPD request);

    void delete(UUID id);
    
    List<E> findRelevantForAssistant(String query);
}

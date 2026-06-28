package com.catalog.framework.mapper;

import com.catalog.framework.domain.Entry;
import com.catalog.framework.dto.EntryRequest;
import com.catalog.framework.dto.EntryResponse;

public interface EntryMapper<
        E extends Entry,
        REQ extends EntryRequest,
        UPD extends EntryUpdateRequest,
        RES extends EntryResponse> {

    E toEntity(REQ request);

    RES toResponse(E entity);

    void updateEntity(E entity, REQ request);

    void partialUpdateEntity(E entity, UPD request);

    default void mapBaseFields(E entity, RES response) {
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());
    }
}

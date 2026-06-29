package com.catalog.framework.controller;

import com.catalog.framework.domain.Entry;
import com.catalog.framework.domain.EntrySortField;
import com.catalog.framework.dto.EntryRequest;
import com.catalog.framework.dto.EntryResponse;
import com.catalog.framework.dto.EntryUpdateRequest;
import com.catalog.framework.service.EntryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

public abstract class EntryController<
        E extends Entry,
        REQ extends EntryRequest,
        UPD extends EntryUpdateRequest,
        RES extends EntryResponse,
        F,
        S extends Enum<S> & EntrySortField> {

    protected abstract EntryService<E, REQ, UPD, RES, F> getService();

    /**
     * Hot spot: subclasses informam a classe do enum de campos ordenáveis.
     */
    protected abstract Class<S> getSortFieldClass();

    /**
     * Hot spot.
     * Constrói o objeto de filtros específico do domínio a partir
     * dos parâmetros da requisição.
     */
    protected abstract F buildFilters(Map<String, String[]> params);

    /**
     * Hot spot: subclasses informam o caminho base para o header Location no POST.
     */
    protected abstract String getBasePath();

    @PostMapping
    public ResponseEntity<RES> create(@Valid @RequestBody REQ request) {
        RES created = getService().create(request);
        return ResponseEntity.created(
                URI.create(getBasePath() + "/" + created.getId()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<Page<RES>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            HttpServletRequest request) {

        Sort sort = buildSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                getService().list(buildFilters(request.getParameterMap()), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RES> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(getService().getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RES>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                getService().search(
                        query,
                        buildFilters(request.getParameterMap()),
                        sortBy,
                        sortDir,
                        pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RES> update(
            @PathVariable UUID id,
            @Valid @RequestBody REQ request) {

        return ResponseEntity.ok(getService().update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RES> partialUpdate(
            @PathVariable UUID id,
            @RequestBody UPD request) {

        return ResponseEntity.ok(getService().partialUpdate(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------------------------
    // Frozen spot: valida e constrói a ordenação
    // -------------------------------------------------------------------------

    private Sort buildSort(String sortBy, String sortDir) {

        if (sortBy == null || sortBy.isBlank()) {
            return Sort.unsorted();
        }

        EntrySortField.fromString(getSortFieldClass(), sortBy);

        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        return Sort.by(direction, sortBy);
    }

    // -------------------------------------------------------------------------
    // Frozen spot: helpers para construção dos filtros
    // -------------------------------------------------------------------------

    protected final String getParam(Map<String, String[]> params, String name) {
        String[] values = params.get(name);

        if (values == null || values.length == 0) {
            return null;
        }

        String value = values[0].trim();
        return value.isEmpty() ? null : value;
    }

    protected final Integer getInteger(Map<String, String[]> params, String name) {
        String value = getParam(params, name);
        return value == null ? null : Integer.valueOf(value);
    }

    protected final Long getLong(Map<String, String[]> params, String name) {
        String value = getParam(params, name);
        return value == null ? null : Long.valueOf(value);
    }

    protected final BigDecimal getBigDecimal(Map<String, String[]> params, String name) {
        String value = getParam(params, name);
        return value == null ? null : new BigDecimal(value);
    }

    protected final Boolean getBoolean(Map<String, String[]> params, String name) {
        String value = getParam(params, name);
        return value == null ? null : Boolean.valueOf(value);
    }

    protected final UUID getUUID(Map<String, String[]> params, String name) {
        String value = getParam(params, name);
        return value == null ? null : UUID.fromString(value);
    }
}
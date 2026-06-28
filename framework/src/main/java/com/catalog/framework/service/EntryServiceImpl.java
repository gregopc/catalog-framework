package com.catalog.framework.service;

import com.catalog.framework.domain.Entry;
import com.catalog.framework.dto.EntryRequest;
import com.catalog.framework.dto.EntryResponse;
import com.catalog.framework.dto.EntryUpdateRequest;
import com.catalog.framework.exception.EntityNotFoundException;
import com.catalog.framework.mapper.EntryMapper;
import com.catalog.framework.repository.EntryRepository;
import com.catalog.framework.repository.EntrySpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

public abstract class EntryServiceImpl<
        E extends Entry,
        REQ extends EntryRequest,
        UPD extends EntryUpdateRequest,
        RES extends EntryResponse,
        F>
        implements EntryService<E, REQ, UPD, RES, F> {

    // ─── Frozen spots: infraestrutura fornecida pelo domínio ─────────────────

    protected abstract EntryRepository<E> getRepository();
    protected abstract EntrySpecification<E, F> getSpecification();
    protected abstract EntryMapper<E, REQ, UPD, RES> getMapper();

    // ─── Hot spots: subclasses implementam por domínio ───────────────────────

    /**
     * Hot spot: valida regras de negócio antes de criar ou atualizar completamente.
     */
    protected abstract void validateOnSave(REQ request);

    /**
     * Hot spot: valida regras de negócio antes de atualizar parcialmente.
     */
    protected abstract void validateOnPartialUpdate(UPD request);

    /**
     * Hot spot: define o Comparator para ordenação explícita por campo.
     */
    protected abstract Comparator<E> buildComparator(String sortBy);

    // ─── Frozen spots: CRUD e algoritmo de busca ─────────────────────────────

    private static final Set<String> STOPWORDS = Set.of(
            "para", "com", "de", "o", "a", "e", "do", "da",
            "the", "of", "and", "for", "in", "on"
    );

    @Override
    public RES create(REQ request) {
        validateOnSave(request);
        E entity = getMapper().toEntity(request);
        return getMapper().toResponse(getRepository().save(entity));
    }

    @Override
    public Page<RES> list(F filters, Pageable pageable) {
        Specification<E> spec = getSpecification().withFilters(filters);
        return getRepository().findAll(spec, pageable).map(getMapper()::toResponse);
    }

    @Override
    public RES getById(UUID id) {
        E entry = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entry not found: " + id));
        return getMapper().toResponse(entry);
    }

    @Override
    public Page<RES> search(
            String query,
            F filters,
            String sortBy,
            String sortDir,
            Pageable pageable) {

        Specification<E> spec = getSpecification().withFilters(filters);
        List<E> entries = getRepository().findAll(spec);

        String normalizedQuery = normalize(query);
        List<String> terms = Arrays.stream(normalizedQuery.split(" "))
                .filter(t -> !t.isBlank())
                .filter(t -> !STOPWORDS.contains(t))
                .toList();

        List<E> scored = entries.stream()
                .map(entry -> Map.entry(entry, score(entry, terms)))
                .filter(e -> e.getValue() > 0)
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));

        if (sortBy != null && !sortBy.isBlank()) {
            Comparator<E> comparator = buildComparator(sortBy);
            if ("desc".equalsIgnoreCase(sortDir)) comparator = comparator.reversed();
            scored.sort(comparator);
        }

        List<RES> ranked = scored.stream().map(getMapper()::toResponse).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), ranked.size());
        List<RES> content = start >= ranked.size() ? List.of() : ranked.subList(start, end);

        return new PageImpl<>(content, pageable, ranked.size());
    }

    @Override
    public RES update(UUID id, REQ request) {
        validateOnSave(request);
        E existing = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entry not found: " + id));
        getMapper().updateEntity(existing, request);
        return getMapper().toResponse(getRepository().save(existing));
    }

    @Override
    public RES partialUpdate(UUID id, UPD request) {
        validateOnPartialUpdate(request);
        E existing = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entry not found: " + id));
        getMapper().partialUpdateEntity(existing, request);
        return getMapper().toResponse(getRepository().save(existing));
    }

    @Override
    public void delete(UUID id) {
        if (!getRepository().existsById(id))
            throw new EntityNotFoundException("Entry not found: " + id);
        getRepository().deleteById(id);
    }

    @Override
    public List<E> findRelevantForAssistant(String query) {
        List<E> entries = getRepository().findAll();

        String normalizedQuery = normalize(query);
        List<String> terms = Arrays.stream(normalizedQuery.split(" "))
                .filter(t -> !STOPWORDS.contains(t))
                .toList();

        return entries.stream()
                .map(entry -> Map.entry(entry, score(entry, terms)))
                .filter(e -> e.getValue() > 0)
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();
    }

    // ─── Frozen spot: algoritmo de scoring por campos configuráveis ──────────

    private int score(E entry, List<String> terms) {
        int total = 0;
        for (Map.Entry<String, Integer> field : entry.getSearchableFields().entrySet()) {
            String normalized = normalize(field.getKey());
            int weight = field.getValue();
            for (String term : terms) {
                if (normalized.contains(term)) total += weight;
            }
        }
        return total;
    }

    protected String normalize(String text) {
        if (text == null) return "";
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", "");
    }
}

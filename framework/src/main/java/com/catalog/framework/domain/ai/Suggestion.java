package com.catalog.framework.domain.ai;

import java.util.List;
import com.catalog.framework.dto.EntryResponse;

public record Suggestion (List<? extends EntryResponse> suggestedProducts, String reply) {}

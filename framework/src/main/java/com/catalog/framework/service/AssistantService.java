package com.catalog.framework.service;

import com.catalog.framework.dto.ai.ChatResponse;
import com.catalog.framework.dto.ai.ChatSuggestionRequest;
import com.catalog.framework.dto.ai.ChatSuggestionResponse;

public interface AssistantService {
    ChatResponse chat(String message);
    ChatSuggestionResponse chatSuggestion(ChatSuggestionRequest request);
}

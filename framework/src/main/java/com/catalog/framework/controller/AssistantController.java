package com.catalog.framework.controller;

import com.catalog.framework.dto.ai.*;
import com.catalog.framework.service.AssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Assistant", description = "Assistente inteligente do catálogo")
@RestController
@RequestMapping("/assistant")
@RequiredArgsConstructor
public abstract class AssistantController {

    protected abstract AssistantService getService();

    @Operation(summary = "Chat com o assistente",
               description = "Recebe mensagem do usuário e retorna resposta contextual")
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        return ResponseEntity.ok(getService().chat(request.getMessage()));
    }

    @Operation(summary = "Sugestões do assistente",
               description = "Recebe histórico de mensagens e retorna resposta + itens recomendados do catálogo")
    @PostMapping("/suggestion")
    public ResponseEntity<ChatSuggestionResponse> suggestion(@RequestBody ChatSuggestionRequest request) {
        return ResponseEntity.ok(getService().chatSuggestion(request));
    }
}

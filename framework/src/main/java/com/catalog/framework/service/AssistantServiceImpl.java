package com.catalog.framework.service;

import com.catalog.framework.ai.AIClient;
import com.catalog.framework.domain.Entry;
import com.catalog.framework.domain.ai.ChatHistoryEntry;
import com.catalog.framework.domain.ai.Suggestion;
import com.catalog.framework.dto.EntryRequest;
import com.catalog.framework.dto.EntryResponse;
import com.catalog.framework.dto.EntryUpdateRequest;
import com.catalog.framework.dto.ai.*;
import com.catalog.framework.mapper.EntryMapper;
import lombok.RequiredArgsConstructor;

import java.text.Normalizer;
import java.util.List;

@RequiredArgsConstructor
public abstract class AssistantServiceImpl<
        E extends Entry,
        REQ extends EntryRequest,
        UPD extends EntryUpdateRequest,
        RES extends EntryResponse,
        F>
        implements AssistantService {

    private final EntryService<E, REQ, UPD, RES, F> entryService;
    private final EntryMapper<E, REQ, UPD, RES> entryMapper;
    private final AIClient aiClient;

    // ─── Frozen spot: pipeline de IA ─────────────────────────────────────────

    @Override
    public ChatResponse chat(String message) {
        String searchQuery = cleanKeywords(aiClient.extractKeywords(message));
        List<E> entries = entryService.findRelevantForAssistant(searchQuery);
        String reply = aiClient.generateReply(buildPrompt(message, entries));
        return new ChatResponse(reply);
    }

    @Override
    public ChatSuggestionResponse chatSuggestion(ChatSuggestionRequest request) {
        String lastMessage = request.getMessages().stream()
                .filter(e -> "user".equalsIgnoreCase(e.role()))
                .reduce((first, second) -> second)
                .map(ChatHistoryEntry::content)
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma mensagem do usuário encontrada"));

        String searchQuery = cleanKeywords(aiClient.extractKeywords(lastMessage));
        List<E> relevant = entryService.findRelevantForAssistant(searchQuery);

        if (relevant.isEmpty()) {
            String emptyReply = "Não encontrei itens disponíveis no catálogo para essa necessidade no momento.";
            return ChatSuggestionResponse.builder()
                    .reply(emptyReply)
                    .suggestion(new Suggestion(List.of(), emptyReply))
                    .build();
        }

        List<String> selectedNames = aiClient.extractEntryNames(
                buildSelectionPrompt(request.getMessages(), relevant));

        List<E> suggested = relevant.stream()
                .filter(entry -> selectedNames.stream()
                        .anyMatch(name ->
                                normalize(entry.getDisplayName()).contains(normalize(name)) ||
                                normalize(name).contains(normalize(entry.getDisplayName()))))
                .toList();

        List<RES> suggestedResponses = suggested.stream().map(entryMapper::toResponse).toList();
        String reply = aiClient.generateReply(buildReplyPrompt(lastMessage, suggested));

        return ChatSuggestionResponse.builder()
                .reply(reply)
                .suggestion(new Suggestion(suggestedResponses, reply))
                .build();
    }

    // ─── Frozen spot: construção de prompts ──────────────────────────────────

    private String buildSelectionPrompt(List<ChatHistoryEntry> messages, List<E> entries) {
        StringBuilder sb = new StringBuilder();
        sb.append("Histórico da conversa:\n");
        for (ChatHistoryEntry entry : messages) {
            sb.append(entry.role()).append(": ").append(entry.content()).append("\n");
        }
        sb.append("\nItens disponíveis no catálogo:\n");
        for (E entry : entries) {
            sb.append("- ").append(entry.getDisplayName()).append("\n");
        }
        sb.append("""

        Tarefa: Identifique quais itens da lista o usuário vai precisar.

        REGRAS:
        - Retorne APENAS um array JSON com os nomes EXATOS da lista
        - NÃO inclua itens que não estão na lista
        - NÃO adicione explicações, apenas o JSON
        - Se nenhum for relevante, retorne []

        Resposta (somente JSON):
        """);
        return sb.toString();
    }

    private String buildReplyPrompt(String lastMessage, List<E> entries) {
        StringBuilder sb = new StringBuilder();
        sb.append("O usuário perguntou: ").append(lastMessage).append("\n\n");
        sb.append("Itens encontrados no catálogo:\n");
        for (E entry : entries) {
            sb.append("- ").append(entry.getDisplayName())
                    .append(" | ").append(entry.getDisplaySummary()).append("\n");
        }
        sb.append("""

        Instruções:
        - Informe ao usuário que encontramos esses itens no catálogo
        - Mencione apenas os itens listados acima
        - NÃO invente ou mencione itens que não estão na lista
        - Seja objetivo e amigável
        """);
        return sb.toString();
    }

    private String buildPrompt(String message, List<E> entries) {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuário disse: ").append(message).append("\n\n");
        sb.append("Itens disponíveis no catálogo:\n");
        for (int i = 0; i < entries.size(); i++) {
            E entry = entries.get(i);
            sb.append(i + 1).append(". ")
                    .append(entry.getDisplayName())
                    .append(" - ").append(entry.getDisplaySummary()).append("\n");
        }
        sb.append("""

        Instruções:
        - Você é um assistente do catálogo
        - Recomende itens com base na lista
        - Não invente itens
        - Seja claro e objetivo
        """);
        return sb.toString();
    }

    private String cleanKeywords(String raw) {
        return raw.toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private String normalize(String text) {
        if (text == null) return "";
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", "");
    }
}

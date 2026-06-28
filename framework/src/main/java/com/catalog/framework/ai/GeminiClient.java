package com.catalog.framework.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Component
@RequiredArgsConstructor
public class GeminiClient implements AIClient {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    @Value("${catalog.ai.domain-hint:Foque nos termos mais relevantes para busca}")
    private String domainHint;

    private final WebClient webClient = WebClient.create("https://generativelanguage.googleapis.com");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String generateReply(String prompt) {
        return extractTextFromResponse(getModelResponse(prompt));
    }

    @Override
    public String extractKeywords(String message) {
        String prompt = "Extraia os principais termos de busca da frase abaixo.\n\n"
                + "Regras:\n- Responda apenas com palavras separadas por espaço\n- Não explique nada\n- "
                + domainHint + "\n\nFrase:\n" + message;
        return generateReply(prompt);
    }

    @Override
    public List<String> extractEntryNames(String prompt) {
        String response = extractTextFromResponse(getModelResponse(prompt));

        try {
            JsonNode node = objectMapper.readTree(response.trim());
            if (node.isArray()) {
                List<String> names = new ArrayList<>();
                node.forEach(n -> names.add(n.asText()));
                return names;
            }
        } catch (Exception ignored) {}

        return Arrays.stream(response.split("[,\n]"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    private String getModelResponse(String prompt) {
        return webClient.post()
                .uri("/v1/models/" + model + ":generateContent")
                .header("x-goog-api-key", apiKey)
                .bodyValue(Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String extractTextFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode candidates = root.path("candidates");
            if (!candidates.isArray() || candidates.isEmpty()) return "Não consegui gerar resposta no momento.";
            JsonNode parts = candidates.get(0).path("content").path("parts");
            if (!parts.isArray() || parts.isEmpty()) return "Resposta inválida da IA.";
            return parts.get(0).path("text").asText();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar resposta da IA", e);
        }
    }
}

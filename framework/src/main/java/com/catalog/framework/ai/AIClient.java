package com.catalog.framework.ai;

import java.util.List;

public interface AIClient {

    String generateReply(String prompt);

    String extractKeywords(String message);

    List<String> extractEntryNames(String prompt);
}

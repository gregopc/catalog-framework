package com.catalog.framework.exception;

import lombok.*;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ApiError {
    private final String message;
    private final Map<String, String> errors;
}

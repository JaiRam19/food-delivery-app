package com.codewave.auth_service.util;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}

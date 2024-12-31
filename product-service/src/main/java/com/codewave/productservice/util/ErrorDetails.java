package com.codewave.productservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class ErrorDetails {
    private String message;
    private LocalDateTime timestamp;
    private String details;
}

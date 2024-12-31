package com.codewave.orderservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class ErrorDetails {
    private String message;
    private LocalDateTime timestamp;
    private String description;
}

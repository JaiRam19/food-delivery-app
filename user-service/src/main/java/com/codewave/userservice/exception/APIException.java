package com.codewave.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class APIException extends RuntimeException {
    private String message;
    private HttpStatus status;
}

package com.codewave.userservice.exception;

import com.codewave.userservice.util.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                        WebRequest request) {
        ErrorDetails details = new ErrorDetails(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = APIException.class)
    public ResponseEntity<ErrorDetails> handleAPIException(APIException e,
                                                           WebRequest request) {
        ErrorDetails details = new ErrorDetails(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
        return new ResponseEntity<>(details, e.getStatus());
    }
}

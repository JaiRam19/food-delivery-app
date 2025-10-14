package com.codewave.auth_service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class APIException extends RuntimeException{

    private HttpStatus status;

    public APIException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

}

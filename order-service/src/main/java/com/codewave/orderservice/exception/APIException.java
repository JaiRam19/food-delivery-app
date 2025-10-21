package com.codewave.orderservice.exception;

import com.codewave.orderservice.util.CustomErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Setter
@Getter
public class APIException extends RuntimeException{
    private CustomErrorResponse errorResponse;

    public APIException(String message){
        super(message);
    }
}

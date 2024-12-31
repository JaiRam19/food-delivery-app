package com.codewave.addressService.exception;

import com.codewave.addressService.util.CustomErrorCodes;
import com.codewave.addressService.util.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AddressServiceGlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage(ex.getMessage())
                .errorCode(CustomErrorCodes.ERROR_ADDRESS_NOT_FOUND)
                .build();
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(ProductServiceBusinessException.class)
    public ResponseEntity<?> handleProductServiceBusinessException(ProductServiceBusinessException ex){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(CustomErrorCodes.GENERIC_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}

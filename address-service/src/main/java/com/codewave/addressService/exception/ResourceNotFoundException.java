package com.codewave.addressService.exception;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

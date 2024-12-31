package com.codewave.addressService.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomErrorResponse {
    private String errorMessage;
    private String errorCode;
    private HttpStatus status;
}

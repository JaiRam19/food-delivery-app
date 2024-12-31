package com.codewave.orderservice.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    @JsonProperty
    private String errorMessage;
    @JsonProperty
    private String errorCode;
    @JsonProperty
    private HttpStatus status;
}

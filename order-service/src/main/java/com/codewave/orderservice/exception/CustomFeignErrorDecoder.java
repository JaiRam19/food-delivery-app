package com.codewave.orderservice.exception;

import com.codewave.orderservice.util.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class CustomFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CustomErrorResponse errorDetails = mapper.readValue(response.body().asInputStream(), CustomErrorResponse.class);
            return new APIException(errorDetails);
        } catch (IOException e) {
            return new RuntimeException("Failed to decode error response", e);
        }

    }
}

package com.codewave.productservice.exception;


public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("Resource %s not found for field %s of value %s", resourceName, fieldName, fieldValue));
    }
}

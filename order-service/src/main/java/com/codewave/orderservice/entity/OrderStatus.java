package com.codewave.orderservice.entity;

public enum OrderStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private final String status;

    // Constructor to assign values
    OrderStatus(String status) {
        this.status = status;
    }

    // Getters to retrieve the values
    public String getStatus() {
        return status;
    }

}

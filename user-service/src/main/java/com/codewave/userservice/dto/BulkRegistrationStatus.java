package com.codewave.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BulkRegistrationStatus {
    private String requestId;
    private String status;
    private int totalCount;
    private int processedCount;
    private int successCount;
    private int failureCount;
    private String failureFilePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

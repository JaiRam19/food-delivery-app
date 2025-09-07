package com.codewave.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bulk_register_request")
public class BulkRegisterRequest {
    @Id
    private String requestId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_count", nullable = false)
    private int totalCount;

    @Column(name = "processed_count")
    private int processedCount;

    @Column(name = "success_count")
    private int successCount;

    @Column(name = "failure_count")
    private int failureCount;

    @Column(name = "failure_file_path")
    private String failureFilePath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

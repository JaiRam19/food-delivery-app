package com.codewave.userservice.repository;

import com.codewave.userservice.entity.BulkRegisterRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkRegisterRequestRepository extends JpaRepository<BulkRegisterRequest, String> {
}

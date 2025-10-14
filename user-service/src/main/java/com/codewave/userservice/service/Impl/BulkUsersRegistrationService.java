package com.codewave.userservice.service.Impl;

import com.codewave.userservice.dto.BulkRegistrationStatus;
import com.codewave.userservice.entity.BulkRegisterRequest;
import com.codewave.userservice.mapper.BulkRequestMapper;
import com.codewave.userservice.repository.BulkRegisterRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class BulkUsersRegistrationService {

    private final BulkRegisterRequestRepository bulkRepo;
    private final BulkRequestMapper mapper;

    public BulkRegistrationStatus initialResponse(MultipartFile file){
        BulkRegistrationStatus initialStatus = new BulkRegistrationStatus();
        initialStatus.setRequestId("REQ250907");
        initialStatus.setStatus("ACCEPTED");
        initialStatus.setTotalCount(0);
        initialStatus.setProcessedCount(0);
        initialStatus.setSuccessCount(0);
        initialStatus.setFailureCount(0);
        initialStatus.setFailureFilePath("N/A");
        initialStatus.setCreatedAt(LocalDateTime.now());
        initialStatus.setUpdatedAt(LocalDateTime.now());
        BulkRegisterRequest bulkRequest = mapper.mapToEntity(initialStatus);
        bulkRepo.save(bulkRequest);
        return initialStatus;
    }

    public void processFile(MultipartFile file) {

    }
}

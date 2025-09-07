package com.codewave.userservice.service.Impl;

import com.codewave.userservice.dto.BulkRegistrationStatus;
import com.codewave.userservice.entity.BulkRegisterRequest;
import com.codewave.userservice.exception.APIException;
import com.codewave.userservice.mapper.BulkRequestMapper;
import com.codewave.userservice.repository.BulkRegisterRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

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
        initialStatus.setTotalCount(getTotalInputCount(file));
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

    private int getTotalInputCount(MultipartFile file) {
        log.info("BulkUsersRegistrationService::getTotalInputCount() method execution started");

        String filename = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
        log.info("input file name, {}", filename);

        int totalInputCount;
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            totalInputCount = (int) bufferedReader.lines().count();
        } catch (IOException e) {
            throw new APIException("Error while getting the input file record count", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return totalInputCount;
    }

    public void processFile(MultipartFile file) {

    }
}

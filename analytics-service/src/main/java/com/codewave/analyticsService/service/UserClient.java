package com.codewave.analyticsService.service;

import com.codewave.analyticsService.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "http://localhost:8081", value = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/users/all-users")
    List<UserDto> findAllUsers();
}

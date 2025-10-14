package com.codewave.userservice.service;

import com.codewave.userservice.dto.BulkRegistrationStatus;
import com.codewave.userservice.dto.UserDto;
import com.codewave.userservice.dto.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    UserRequest register(UserRequest request);
    UserDto getUserByUserId(Long userId);
    UserDto updateUser(UserDto userDto, Long id);
    String deleteUserById(Long userId);
    List<UserDto> findAllUsers();
    BulkRegistrationStatus bulkRegistration(MultipartFile file);

    BulkRegistrationStatus checkStatus(String requestId);
}

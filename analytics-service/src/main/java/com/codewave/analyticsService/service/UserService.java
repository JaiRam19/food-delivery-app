package com.codewave.analyticsService.service;

import com.codewave.analyticsService.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();
}

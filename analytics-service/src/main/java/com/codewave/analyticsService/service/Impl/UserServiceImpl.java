package com.codewave.analyticsService.service.Impl;

import com.codewave.analyticsService.dto.UserDto;
import com.codewave.analyticsService.service.UserClient;
import com.codewave.analyticsService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserClient userClient;
    @Override
    public List<UserDto> findAllUsers() {
        return userClient.findAllUsers();
    }
}

package com.codewave.userservice.service;

import com.codewave.userservice.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto register(UserDto userDto);
    UserDto getUserByUserId(Long userId);
    UserDto updateUser(UserDto userDto);
    String deleteUserById(Long userId);
    List<UserDto> findAllUsers();
}

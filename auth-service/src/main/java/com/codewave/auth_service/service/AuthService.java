package com.codewave.auth_service.service;

import com.codewave.auth_service.dto.UserLoingDto;
import com.codewave.auth_service.dto.UserRegisterDto;
import com.codewave.auth_service.dto.UserRolesDto;

public interface AuthService {
    String register(UserRegisterDto userInfo);
    String authenticateAndGenerateToken(UserLoingDto loginDetails);
    String validateToken(String token);
}

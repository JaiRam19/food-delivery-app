package com.codewave.auth_service.service;

import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserRoles;

public interface RolesService {
    UserRoles createUserRole(UserCredentials user, String role);
}

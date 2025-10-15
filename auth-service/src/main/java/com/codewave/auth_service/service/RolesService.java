package com.codewave.auth_service.service;

import com.codewave.auth_service.dto.UserRolesDto;
import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserRoles;

import java.util.Set;

public interface RolesService {
    UserRolesDto manageRoles(UserRolesDto rolesDto);
    Set<UserRoles> createUserRoles(UserCredentials user, Set<String> inputRoles);
}

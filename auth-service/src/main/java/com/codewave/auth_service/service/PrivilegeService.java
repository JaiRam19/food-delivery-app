package com.codewave.auth_service.service;

import com.codewave.auth_service.dto.UserPrivilegesDto;
import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserPrivileges;

import java.util.Set;

public interface PrivilegeService {
    Set<UserPrivileges> createUserPrivileges(UserCredentials user, Set<String> inputPrivileges);
    UserPrivilegesDto managePrivileges(UserPrivilegesDto privilegesDto);
}

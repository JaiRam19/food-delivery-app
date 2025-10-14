package com.codewave.auth_service.service;

import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserPrivileges;

public interface PrivilegeService {

    UserPrivileges createUserPrivilege(UserCredentials user, String privilege);
}

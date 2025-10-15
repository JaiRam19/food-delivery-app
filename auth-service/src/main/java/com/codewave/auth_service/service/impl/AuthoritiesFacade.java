package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.dto.UserPrivilegesDto;
import com.codewave.auth_service.dto.UserRolesDto;
import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserPrivileges;
import com.codewave.auth_service.entity.UserRoles;
import com.codewave.auth_service.service.PrivilegeService;
import com.codewave.auth_service.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthoritiesFacade {

    @Autowired
    private RolesService rolesService;
    @Autowired
    private PrivilegeService privilegeService;

    public Set<UserRoles> createUserRoles(UserCredentials user, Set<String> inputRoles){
        return rolesService.createUserRoles(user, inputRoles);
    }

    public UserRolesDto manageRoles(UserRolesDto rolesDto){
        return rolesService.manageRoles(rolesDto);
    }

    public Set<UserPrivileges> createUserPrivileges(UserCredentials user, Set<String> inputPrivileges){
        return privilegeService.createUserPrivileges(user, inputPrivileges);
    }

    public UserPrivilegesDto managePrivileges(UserPrivilegesDto privilegesDto){
        return privilegeService.managePrivileges(privilegesDto);
    }


}

package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserPrivileges;
import com.codewave.auth_service.exception.APIException;
import com.codewave.auth_service.repository.PrivilegesRepository;
import com.codewave.auth_service.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegesRepository repository;

    @Override
    public UserPrivileges createUserPrivilege(UserCredentials user, String privilege) {
        UserPrivileges userPrivilege = new UserPrivileges();
        userPrivilege.setPrivilege(privilege);
        userPrivilege.setIsPrivilegeActive(true);
        userPrivilege.setCreateAt(LocalDateTime.now());
        userPrivilege.setUpdatedAt(LocalDateTime.now());
        userPrivilege.setLastMaintenanceUsername(user.getUsername());

        userPrivilege.getUser().add(user);
        user.getPrivileges().add(userPrivilege);

        try{
            return repository.save(userPrivilege);
        }catch (Exception ex){
            throw new APIException("Exception occurred while saving user privilege", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

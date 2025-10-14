package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserRoles;
import com.codewave.auth_service.exception.APIException;
import com.codewave.auth_service.repository.RolesRepository;
import com.codewave.auth_service.service.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;


    @Override
    public UserRoles createUserRole(UserCredentials user, String role) {

        UserRoles userRole = new UserRoles();
        userRole.setRole(role);
        userRole.setIsRoleActive(true);
        userRole.setCreateAt(LocalDateTime.now());
        userRole.setUpdatedAt(LocalDateTime.now());
        userRole.setLastMaintenanceUsername(user.getUsername());

        userRole.getUser().add(user);
        user.getRoles().add(userRole);

        try{
            return rolesRepository.save(userRole);
        }catch (Exception ex){
            log.info("Exception {}", ex.fillInStackTrace());
            throw new APIException("Exception occurred while saving user role "+ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.dto.UserRolesDto;
import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserRoles;
import com.codewave.auth_service.exception.APIException;
import com.codewave.auth_service.repository.RolesRepository;
import com.codewave.auth_service.repository.UserCredentialRepository;
import com.codewave.auth_service.service.RolesService;
import com.codewave.auth_service.util.RoleAndAuthorities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserCredentialRepository repository;

    @Override
    public Set<UserRoles> createUserRoles(UserCredentials user, Set<String> inputRoles) {
        Set<UserRoles> userRoles = new HashSet<>();
        for (String role : inputRoles) {
            UserRoles userRole = createRole(user, role);
            userRoles.add(userRole);
        }
        return userRoles;
    }

    @Override
    public UserRolesDto manageRoles(UserRolesDto rolesDto) {
        //Check if the roles are really received or not
        if(rolesDto.getRoles().isEmpty()){
            throw new APIException("Roles should not be empty", HttpStatus.BAD_REQUEST);
        }

        //Get the user
        UserCredentials user = repository.findByUsername(rolesDto.getUsername())
                .orElseThrow(() -> new APIException("User not found with username: "+ rolesDto.getUsername(), HttpStatus.NOT_FOUND));

        //save extra added roles
        Set<UserRoles> userRoles = createUserRoles(user, rolesDto.getRoles());

        //update the user with new roles
        userRoles.forEach(role -> user.getRoles().add(role));

        //save the user again
        UserCredentials savedUser = repository.save(user);

        //clear existing roles and rebuild response with saved roles
        rolesDto.getRoles().clear();
        savedUser.getRoles().forEach(userRole -> rolesDto.getRoles().add(userRole.getRole()));
        return rolesDto;
    }

    private UserRoles createRole(UserCredentials user, String role) {

        if(!RoleAndAuthorities.isValidRole(role))
            throw new APIException("Invalid role: "+role+", username: "+user.getUsername(), HttpStatus.BAD_REQUEST);

        if(user.getRoles().stream().anyMatch(r -> r.getRole().equals(role)))
            throw new APIException(role + " - authority already exist for the user: "+user.getUsername(), HttpStatus.BAD_REQUEST);

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
            throw new APIException("Exception occurred while saving user role "+ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

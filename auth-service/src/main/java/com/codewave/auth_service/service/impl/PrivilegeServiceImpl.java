package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.dto.UserPrivilegesDto;
import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserPrivileges;
import com.codewave.auth_service.exception.APIException;
import com.codewave.auth_service.repository.PrivilegesRepository;
import com.codewave.auth_service.repository.UserCredentialRepository;
import com.codewave.auth_service.service.PrivilegeService;
import com.codewave.auth_service.util.RoleAndAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegesRepository privilegesRepository;
    @Autowired
    private UserCredentialRepository repository;


    public Set<UserPrivileges> createUserPrivileges(UserCredentials user, Set<String> inputPrivileges) {
        Set<UserPrivileges> userPrivileges = new HashSet<>();
        for (String privilege : inputPrivileges) {
            UserPrivileges userPrivilege = createPrivilege(user, privilege);
            userPrivileges.add(userPrivilege);
        }
        return userPrivileges;
    }

    private UserPrivileges createPrivilege(UserCredentials user, String privilege) {

        if(!RoleAndAuthorities.isValidPrivilege(privilege))
            throw new APIException("Invalid role: "+privilege+", username: "+user.getUsername(), HttpStatus.BAD_REQUEST);

        if(user.getPrivileges().stream().anyMatch(p -> p.getPrivilege().equals(privilege)))
            throw new APIException(privilege + " - authority already exist for the user: "+user.getUsername(), HttpStatus.BAD_REQUEST);

        UserPrivileges userPrivilege = new UserPrivileges();
        userPrivilege.setPrivilege(privilege);
        userPrivilege.setIsPrivilegeActive(true);
        userPrivilege.setCreateAt(LocalDateTime.now());
        userPrivilege.setUpdatedAt(LocalDateTime.now());
        userPrivilege.setLastMaintenanceUsername(user.getUsername());

        userPrivilege.getUser().add(user);
        user.getPrivileges().add(userPrivilege);

        try{
            return privilegesRepository.save(userPrivilege);
        }catch (Exception ex){
            throw new APIException("Exception occurred while saving user privilege", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserPrivilegesDto managePrivileges(UserPrivilegesDto privilegesDto) {
        //Check if the roles are really received or not
        if(privilegesDto.getPrivileges().isEmpty()){
            throw new APIException("Privileges should not be empty", HttpStatus.BAD_REQUEST);
        }

        //Get the user
        UserCredentials user = repository.findByUsername(privilegesDto.getUsername())
                .orElseThrow(() -> new APIException("User not found with username: "+privilegesDto.getUsername(), HttpStatus.NOT_FOUND));

        //save extra added privileges
        Set<UserPrivileges> userPrivileges = createUserPrivileges(user, privilegesDto.getPrivileges());

        //update the user with new privileges
        userPrivileges.forEach(privilege -> user.getPrivileges().add(privilege));

        //save the user again
        UserCredentials savedUser = repository.save(user);

        //clear existing privileges and rebuild the response with saved privileges to make sure new privileges are saved
        privilegesDto.getPrivileges().clear();
        savedUser.getPrivileges().forEach(userPrivilege -> privilegesDto.getPrivileges().add(userPrivilege.getPrivilege()));
        return privilegesDto;
    }
}

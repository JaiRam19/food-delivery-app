package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.dto.UserLoingDto;
import com.codewave.auth_service.dto.UserRegisterDto;
import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.exception.APIException;
import com.codewave.auth_service.mapper.UserCredentialMapper;
import com.codewave.auth_service.repository.UserCredentialRepository;
import com.codewave.auth_service.service.AuthService;
import com.codewave.auth_service.service.jwt.JwtService;
import com.codewave.auth_service.util.RoleAndAuthorities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private UserCredentialMapper mapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthoritiesFacade authoritiesFacade;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public String register(UserRegisterDto userInfo) {

        if (repository.existsByUsername(userInfo.getUsername())) {
            throw new APIException("Username already present please create with another", HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByEmail(userInfo.getEmail())) {
            throw new APIException("Email already present please create with another email", HttpStatus.BAD_REQUEST);
        }

        UserCredentials user = mapper.mapToEntity(userInfo);
        user.setPassword(encoder.encode(userInfo.getPassword()));
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        //First save user without setting the roles and privileges
        user = saveUser(user);

        user.setRoles(authoritiesFacade.createUserRoles(user, Set.of(String.valueOf(RoleAndAuthorities.ROLE_USER))));
        user.setPrivileges(authoritiesFacade.createUserPrivileges(user, Set.of(String.valueOf(RoleAndAuthorities.READ_PRIVILEGE))));

        //save the same user after setting the roles and privileges
        saveUser(user);

        return "Registration successful!!!";
    }

    @Override
    public String authenticateAndGenerateToken(UserLoingDto loginDetails) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDetails.getUsername(),
                    loginDetails.getPassword()));

            if (authenticate.isAuthenticated()) {
                return jwtService.generateToken(authenticate);
            } else {
                throw new APIException("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
        } catch (BadCredentialsException ex) {
            throw new APIException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new APIException("Authentication failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UserCredentials saveUser(UserCredentials credentials) {
        UserCredentials user;
        try {
            user = repository.save(credentials);
        } catch (Exception e) {
            throw new APIException("Exception occurred while registering user", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    public String validateToken(String token) {
        System.out.println("AuthServiceImpl:validateToken method execution started");
        try {
            jwtService.validateToken(token);
            return "Token is Valid";
        } catch (Exception e) {
            System.out.println("AuthServiceImpl:validateToken exception occurred "+e.getMessage());
            throw new APIException("Invalid Token", HttpStatus.BAD_REQUEST);
        }

    }

}

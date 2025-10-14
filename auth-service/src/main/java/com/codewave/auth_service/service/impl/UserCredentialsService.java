package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.exception.APIException;
import com.codewave.auth_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCredentialsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> userDetails = Optional.ofNullable(repository.findByUsername(username).orElseThrow(
                () -> new APIException("User not found for username: " + username, HttpStatus.NOT_FOUND)));
        return new CustomUserDetails(userDetails.get());
    }
}

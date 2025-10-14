package com.codewave.auth_service.repository;

import com.codewave.auth_service.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

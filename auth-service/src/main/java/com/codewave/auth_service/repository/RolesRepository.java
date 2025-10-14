package com.codewave.auth_service.repository;

import com.codewave.auth_service.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<UserRoles, Long> {
}

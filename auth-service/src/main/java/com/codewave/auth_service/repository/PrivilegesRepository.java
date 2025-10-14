package com.codewave.auth_service.repository;

import com.codewave.auth_service.entity.UserPrivileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesRepository extends JpaRepository<UserPrivileges, Long> {
}

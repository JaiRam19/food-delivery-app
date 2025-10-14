package com.codewave.userservice.repository;

import com.codewave.userservice.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<UserPreferences, Long> {
}

package com.codewave.auth_service.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesDto {
    private String username;
    private Set<String> roles = new HashSet<>();
}

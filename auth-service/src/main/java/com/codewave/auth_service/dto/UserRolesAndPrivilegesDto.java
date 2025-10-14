package com.codewave.auth_service.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesAndPrivilegesDto {
    private String username;
    private Set<String> roles;
    private Set<String> privileges;
}

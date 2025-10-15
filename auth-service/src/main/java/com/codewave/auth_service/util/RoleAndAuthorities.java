package com.codewave.auth_service.util;

import java.util.Arrays;

public enum RoleAndAuthorities {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_HR,

    READ_PRIVILEGE,
    WRITE_PRIVILEGE,
    UPDATE_PRIVILEGE,
    DELETE_PRIVILEGE;

    public static boolean isValidRole(String role){
        return Arrays.stream(values())
                .filter(v -> v.name().startsWith("ROLE_"))
                .anyMatch(roles -> roles.name().equals(role));
    }

    public static boolean isValidPrivilege(String privilege){
        return Arrays.stream(values())
                .filter(v -> v.name().endsWith("_PRIVILEGE"))
                .anyMatch(roles -> roles.name().equals(privilege));
    }
}

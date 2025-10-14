package com.codewave.auth_service.service.impl;

import com.codewave.auth_service.entity.UserCredentials;
import com.codewave.auth_service.entity.UserPrivileges;
import com.codewave.auth_service.entity.UserRoles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<UserRoles> roles;
    private Set<UserPrivileges> privileges;

    public CustomUserDetails(UserCredentials credentials){
        this.username = credentials.getUsername();
        this.password = credentials.getPassword();
        this.roles = credentials.getRoles();
        this.privileges = credentials.getPrivileges();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> rolesAndPrivileges = new HashSet<>();
        //add roles
        roles.forEach(role -> rolesAndPrivileges.add(new SimpleGrantedAuthority("ROLE_"+role.getRole())));

        //add privileges
        privileges.forEach(privileges -> rolesAndPrivileges.add(new SimpleGrantedAuthority(privileges.getPrivilege())));

        return rolesAndPrivileges;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

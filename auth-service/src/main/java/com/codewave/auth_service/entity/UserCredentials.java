package com.codewave.auth_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"roles", "privileges"})
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USER_CREDENTIALS")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonManagedReference
    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRoles> roles = new HashSet<>();

    @JsonManagedReference
    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserPrivileges> privileges = new HashSet<>();
}

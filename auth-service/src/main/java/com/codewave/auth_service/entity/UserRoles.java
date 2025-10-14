package com.codewave.auth_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "user")
@Table(name = "USER_ROLES")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private String role;

    private Boolean isRoleActive;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private String lastMaintenanceUsername;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "users_roles_mapping",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")

    )
    private Set<UserCredentials> user = new HashSet<>();

}

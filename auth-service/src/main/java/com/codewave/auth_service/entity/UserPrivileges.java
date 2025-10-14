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
@Table(name = "USER_PRIVILEGES")
public class UserPrivileges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long privilegeId;
    private String privilege;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Boolean isPrivilegeActive;
    private String lastMaintenanceUsername;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "users_privileges_mapping",
            joinColumns = @JoinColumn(name = "privilege_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserCredentials> user = new HashSet<>();
}

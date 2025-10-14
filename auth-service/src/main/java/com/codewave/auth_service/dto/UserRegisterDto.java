package com.codewave.auth_service.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;
}

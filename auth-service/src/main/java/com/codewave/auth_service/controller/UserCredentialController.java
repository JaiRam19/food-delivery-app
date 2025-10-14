package com.codewave.auth_service.controller;

import com.codewave.auth_service.dto.UserLoingDto;
import com.codewave.auth_service.dto.UserRegisterDto;
import com.codewave.auth_service.dto.UserRolesAndPrivilegesDto;
import com.codewave.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserCredentialController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto registerDto){
        return new ResponseEntity<>(service.register(registerDto), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> generate(@RequestBody UserLoingDto request){
        return ResponseEntity.ok(service.authenticateAndGenerateToken(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestParam("token") String token){
        return ResponseEntity.ok(service.validateToken(token));
    }

    @PostMapping("/admin/manage")
    public ResponseEntity<?> manageRolesAndPrivileges(@RequestBody UserRolesAndPrivilegesDto rolesAndPrivileges){
        return ResponseEntity.ok(service.manageRolesAndPrivileges(rolesAndPrivileges));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/welcome")
    public ResponseEntity<String> adminWelcome(){
        return ResponseEntity.ok("Admin, Welcome to spring security implementation demo...");
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/manager/welcome")
    public ResponseEntity<String> managerWelcome(){
        return ResponseEntity.ok("Manager, Welcome to spring security implementation demo...");
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/user/welcome")
    public ResponseEntity<String> userWelcome(){
        return ResponseEntity.ok("User, Welcome to spring security implementation demo...");
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> allWelcome(){
        return ResponseEntity.ok("All, Welcome to spring security implementation demo...");
    }

}

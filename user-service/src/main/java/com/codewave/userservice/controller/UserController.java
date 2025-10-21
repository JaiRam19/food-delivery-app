package com.codewave.userservice.controller;

import com.codewave.userservice.dto.BulkRegistrationStatus;
import com.codewave.userservice.dto.UserDto;
import com.codewave.userservice.dto.UserRequest;
import com.codewave.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    //register user
    @PostMapping("/register")
    public ResponseEntity<UserRequest> register(@RequestBody UserRequest request) {
        UserRequest registerUser = userService.register(request);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    //get user by id
    @PreAuthorize("hasRole('ROLE_USER') and hasAnyAuthority('READ_PRIVILEGE')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getUserByUserId(id));
    }

    //update user details
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserRequest request) {
        UserDto updateUser = userService.updateUser(request.getUserDetails(), id);
        return ResponseEntity.ok(updateUser);
    }

    //delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    //find all users
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAnyAuthority('READ_PRIVILEGE', 'WRITE_PRIVILEGE')")
    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/bulk-register")
    public ResponseEntity<BulkRegistrationStatus> bulkUsersRegistration(@RequestParam("file") MultipartFile file){
        return ResponseEntity.accepted().body(userService.bulkRegistration(file));
    }

    @GetMapping("/bulk-status/{requestId}")
    public ResponseEntity<BulkRegistrationStatus> bulkUserRegistrationStatus(@PathVariable("requestId") String requestId){
        return ResponseEntity.ok(userService.checkStatus(requestId));
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(true);
    }
}

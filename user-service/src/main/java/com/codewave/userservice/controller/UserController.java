package com.codewave.userservice.controller;

import com.codewave.userservice.dto.UserDto;
import com.codewave.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/users")
public class UserController {
    private UserService userService;

    //register user
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto registerUser = userService.register(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    //get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getUserByUserId(id));
    }

    //update user details
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto updateUser = userService.updateUser(userDto);
        return ResponseEntity.ok(updateUser);
    }

    //delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    //find all users
    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }
}

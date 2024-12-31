package com.codewave.userservice.service.Impl;

import com.codewave.userservice.dto.UserDto;
import com.codewave.userservice.entity.User;
import com.codewave.userservice.exception.APIException;
import com.codewave.userservice.exception.ResourceNotFoundException;
import com.codewave.userservice.mapper.UserMapper;
import com.codewave.userservice.repository.UserRepository;
import com.codewave.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public UserDto register(UserDto userDto) {
        //verify if the new user's email and phone number are already present
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new APIException("Email already linked to another account", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new APIException("Phone number already linked to another account", HttpStatus.BAD_REQUEST);
        }

        //save user
        User savedUser = userRepository.save(userMapper.mapToEntity(userDto));
        //convert into dto and return it
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public UserDto getUserByUserId(Long userId) {
        //get user from database if user not found throw Resource not found exception
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "userId", String.valueOf(userId))
        );

        //convert into dto and return
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        //verify if the new user's email and phone number are already present
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new APIException("Email already linked to another account", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new APIException("Phone number already linked to another account", HttpStatus.BAD_REQUEST);
        }

        //find user before updating whether the user present in the databse or not
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("user", "userId", String.valueOf(userDto.getUserId())));
        //set user fields
        user.setUserId(userDto.getUserId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        //save updated user
        User updatedUser = userRepository.save(user);

        //convert to dto and return it
        return userMapper.mapToDto(updatedUser);
    }

    @Override
    public String deleteUserById(Long userId) {
        //check if the user present in database or not before deleting
        userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "userId", String.valueOf(userId)));

        //if user present, proceed to delete
        userRepository.deleteById(userId);

        //return delete message
        return "User deleted successfully";
    }

    @Override
    public List<UserDto> findAllUsers() {
       return userRepository.findAll().stream()
                .map(userMapper::mapToDto)
                .toList();
    }
}

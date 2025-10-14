package com.codewave.userservice.service.Impl;

import com.codewave.userservice.dto.BulkRegistrationStatus;
import com.codewave.userservice.dto.UserDto;
import com.codewave.userservice.dto.UserRequest;
import com.codewave.userservice.entity.BulkRegisterRequest;
import com.codewave.userservice.entity.User;
import com.codewave.userservice.entity.UserPreferences;
import com.codewave.userservice.exception.APIException;
import com.codewave.userservice.exception.ResourceNotFoundException;
import com.codewave.userservice.mapper.BulkRequestMapper;
import com.codewave.userservice.mapper.PreferenceMapper;
import com.codewave.userservice.mapper.UserMapper;
import com.codewave.userservice.repository.BulkRegisterRequestRepository;
import com.codewave.userservice.repository.PreferenceRepository;
import com.codewave.userservice.repository.UserRepository;
import com.codewave.userservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PreferenceRepository preferenceRepository;
    private UserMapper userMapper;
    private PreferenceMapper preferenceMapper;
    private BulkUsersRegistrationService bulkService;
    private BulkRegisterRequestRepository bulkRepo;
    private BulkRequestMapper bulkRequestMapper;
    //private UserRegions regions;

    @Override
    @Transactional
    public UserRequest register(UserRequest request) {
        //verify if the new user's email and phone number are already present
      /*  if(userRepository.existsByEmail(request.getUserDetails().getEmail())) {
            throw new APIException("Email already linked to another account", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByPhoneNumber(request.getUserDetails().getPhoneNumber())) {
            throw new APIException("Phone number already linked to another account", HttpStatus.BAD_REQUEST);
        }*/


        //save user
        User savedUser = null;
        try{
            savedUser = userRepository.save(userMapper.mapToEntity(request.getUserDetails()));
        }catch (DataIntegrityViolationException exception){
            //throw new APIException("Data integrity exception", HttpStatus.CONFLICT);
            System.out.println("Data integrity");
        } catch (Exception e) {
            //throw new APIException("Exception occurred while saving the user details", HttpStatus.BAD_REQUEST);
            System.out.println("Exception");
        }
        System.out.println("Next statements executed");
        UserPreferences preferences = preferenceMapper.mapToEntity(request.getPreferences());
        preferences.setUser(savedUser);
        UserPreferences savedPreferences = preferenceRepository.save(preferences);

        UserRequest userRequest = new UserRequest();
        userRequest.setUserDetails(userMapper.mapToDto(savedUser));
        userRequest.setPreferences(preferenceMapper.mapToDto(savedPreferences));
        //convert into dto and return it
        return userRequest;
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
    public UserDto updateUser(UserDto userDto, Long userId) {
        //verify if the new user's email and phone number are already present
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new APIException("Email already linked to another account", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new APIException("Phone number already linked to another account", HttpStatus.BAD_REQUEST);
        }

        //find user before updating whether the user present in the databse or not
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "userId", String.valueOf(userId)));
        //set user fields
        user.setUserId(userId);
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

    @Override
    public BulkRegistrationStatus bulkRegistration(MultipartFile file) {
        log.info("UserServiceImpl::bulkRegistration() method execution started");
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
        log.info("input file name {}", fileName);
        if(!fileName.endsWith(".csv")){
            throw new APIException("Invalid file format", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        return bulkService.initialResponse(file);
    }

    @Override
    public BulkRegistrationStatus checkStatus(String requestId) {
        log.info("UserService::checkStatus() method execution started");
        log.debug("input request Id - {}", requestId);
        BulkRegisterRequest bulkStatus = bulkRepo.findById(requestId).orElseThrow(
                () -> new APIException("request Id not found", HttpStatus.NOT_FOUND)
        );
        return bulkRequestMapper.mapToDto(bulkStatus);
    }

   /* public String getUserContinentDetails(UserDto user){
        System.out.println("getUserContinentDetails() - method executed");
        return regions.findUserContinent(user);
    }*/
}

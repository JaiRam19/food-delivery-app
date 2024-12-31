package com.codewave.userservice.mapper;

import com.codewave.userservice.dto.UserDto;
import com.codewave.userservice.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public UserDto mapToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    public User mapToEntity(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

}

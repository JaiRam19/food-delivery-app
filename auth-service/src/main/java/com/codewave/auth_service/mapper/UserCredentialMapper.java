package com.codewave.auth_service.mapper;

import com.codewave.auth_service.dto.UserRegisterDto;
import com.codewave.auth_service.entity.UserCredentials;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public UserRegisterDto mapToDto(UserCredentials userInfo) {
        return modelMapper.map(userInfo, UserRegisterDto.class);
    }

    public UserCredentials mapToEntity(UserRegisterDto infoDto) {
        return modelMapper.map(infoDto, UserCredentials.class);
    }
}

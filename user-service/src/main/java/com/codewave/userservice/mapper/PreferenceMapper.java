package com.codewave.userservice.mapper;

import com.codewave.userservice.dto.PreferenceDto;
import com.codewave.userservice.entity.UserPreferences;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PreferenceMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public PreferenceDto mapToDto(UserPreferences preference){
        return modelMapper.map(preference, PreferenceDto.class);
    }

    public UserPreferences mapToEntity(PreferenceDto preferenceDto){
        return modelMapper.map(preferenceDto, UserPreferences.class);
    }
}

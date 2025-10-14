package com.codewave.userservice.mapper;

import com.codewave.userservice.dto.BulkRegistrationStatus;
import com.codewave.userservice.entity.BulkRegisterRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BulkRequestMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public BulkRegisterRequest mapToEntity(BulkRegistrationStatus statusResponse) {
        return modelMapper.map(statusResponse, BulkRegisterRequest.class);
    }

    public BulkRegistrationStatus mapToDto(BulkRegisterRequest status){
        return modelMapper.map(status, BulkRegistrationStatus.class);
    }
}

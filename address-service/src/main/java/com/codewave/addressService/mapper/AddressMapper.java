package com.codewave.addressService.mapper;

import com.codewave.addressService.dto.AddressDto;
import com.codewave.addressService.entity.Address;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AddressDto mapToDto(Address address) {
       return modelMapper.map(address, AddressDto.class);
    }

    public Address mapToEntity(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }

    public ObjectMapper valueMapper(){
        return objectMapper;
    }
}

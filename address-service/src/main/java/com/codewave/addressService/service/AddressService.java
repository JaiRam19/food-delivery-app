package com.codewave.addressService.service;

import com.codewave.addressService.dto.AddressDto;

import java.util.List;


public interface AddressService {
    AddressDto addAddress(AddressDto addressDto);
    AddressDto updateAddress(AddressDto addressDto, Long addressId);
    List<AddressDto> getAllAddressesByUserId(Long userId);
    AddressDto getAddressById(Long addressId);
    String deleteAddressById(Long addressId);
    AddressDto getAddressByUserIdAndAddressId(Long userId, Long addressId);
}

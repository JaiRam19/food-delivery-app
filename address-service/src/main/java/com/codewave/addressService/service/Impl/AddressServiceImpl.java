package com.codewave.addressService.service.Impl;

import com.codewave.addressService.dto.AddressDto;
import com.codewave.addressService.entity.Address;
import com.codewave.addressService.entity.AddressKey;
import com.codewave.addressService.exception.ProductServiceBusinessException;
import com.codewave.addressService.exception.ResourceNotFoundException;
import com.codewave.addressService.mapper.AddressMapper;
import com.codewave.addressService.repository.AddressRepository;
import com.codewave.addressService.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;

    @Override
    public AddressDto addAddress(AddressDto addressDto) {
        AddressDto addressResponse;
        try{
            log.info("AddressServiceImpl::addAddress execution started.");
            Address newAddress = addressMapper.mapToEntity(addressDto);
            log.debug("AddressServiceImpl::addAddress request parameter {}", addressMapper.valueMapper().writeValueAsString(addressDto));

            newAddress.setAddressKey(generateAddressKey(addressDto.getAddressKey().getUserId()));
            newAddress.setCreatedAt(LocalDateTime.now());
            newAddress.setUpdatedAt(LocalDateTime.now());
            Address savedAddress = addressRepository.save(newAddress);
            addressResponse = addressMapper.mapToDto(savedAddress);
            log.debug("AddressServiceImpl::addAddress received response from database {}", savedAddress.toString().toString());

        }catch (Exception ex){
            log.error("Exception occurred while persisting the address into database, exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while saving address into database");
        }

        log.info("AddressServiceImpl::addAddress execution ended.");
        return addressResponse;
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto, Long addressId) {
        Address address = addressRepository.findById(addressId).get();
        address.setFullAddress(addressDto.getFullAddress());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setZipCode(addressDto.getZipCode());
        address.setCountry(addressDto.getCountry());
        address.setUpdatedAt(LocalDateTime.now());

        Address updatedAddress = addressRepository.save(address);
        return addressMapper.mapToDto(updatedAddress);
    }

    @Override
    public List<AddressDto> getAllAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(addressMapper::mapToDto)
                .toList();
    }

    @Override
    public AddressDto getAddressById(Long addressId) {
        return addressMapper.mapToDto(addressRepository.findById(addressId).get());
    }

    @Override
    public String deleteAddressById(Long addressId) {
        addressRepository.deleteById(addressId);
        return "Address deleted successfully";
    }

    @Override
    public AddressDto getAddressByUserIdAndAddressId(Long userId, Long addressId) {
        AddressKey addressKey = new AddressKey();
        addressKey.setAddressId(addressId);
        addressKey.setUserId(userId);
        Address userAddress = addressRepository.findAddressByAddressKey(addressKey);

        if(Objects.isNull(userAddress)){
            log.error("Address not found for user id: {}", userId);
            throw new ResourceNotFoundException("Address id: " + addressId + " not found for user: "+userId);
        }

        return addressMapper.mapToDto(userAddress);
    }

    private AddressKey generateAddressKey(Long userId){
        Long addressId = addressRepository.findMaxAddressId(userId).orElse(0L);
        AddressKey addressKey = new AddressKey();
        addressKey.setAddressId(addressId+1);
        addressKey.setUserId(userId);
        return addressKey;
    }
}

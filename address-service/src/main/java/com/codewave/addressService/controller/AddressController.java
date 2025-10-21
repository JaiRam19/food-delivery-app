package com.codewave.addressService.controller;

import com.codewave.addressService.dto.APIResponse;
import com.codewave.addressService.dto.AddressDto;
import com.codewave.addressService.dto.zipcodeAPIResponse.ApiResponseDTO;
import com.codewave.addressService.mapper.AddressMapper;
import com.codewave.addressService.service.AddressService;
import com.codewave.addressService.service.ZipcodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

import static java.util.stream.DoubleStream.builder;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/address")
public class AddressController {
    private static final String SUCCESS = "Success";
    private AddressService addressService;
    private AddressMapper addressMapper;
    private ZipcodeService zipcodeService;

    //add address
    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody AddressDto addressDto) {
        log.info("AddressController::addAddress request body {}", addressDto.toString());
        AddressDto savedAddress = addressService.addAddress(addressDto);
        APIResponse<AddressDto> apiResponse = APIResponse
                .<AddressDto>builder()
                .status(SUCCESS)
                .result(savedAddress)
                .build();

        log.info("AddressController::addAddress response {}", apiResponse.toString());
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    //update address
    @PutMapping("/update/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody AddressDto addressDto,
                                                    @PathVariable("addressId") Long addressId){
        AddressDto updatedAddress = addressService.updateAddress(addressDto,addressId);
                return ResponseEntity.ok(updatedAddress);
    }

    //get all address for a user
    @GetMapping("/get/all/{userId}")
    public ResponseEntity<List<AddressDto>> getAllAddressesByUserId(@PathVariable("userId") Long userId){
        List<AddressDto> addresses = addressService.getAllAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    //get address by id
    @GetMapping("/get/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable("addressId") Long addressId){
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    //Delete address by id
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable("addressId") Long addressId){
        return ResponseEntity.ok(addressService.deleteAddressById(addressId));
    }

    //get address by userId and addressId
    @GetMapping("/user-address-ids")
    public ResponseEntity<AddressDto> getAddressByUserAndAddressIds(@RequestParam("userId") Long userId,
                                                                    @RequestParam("addressId") Long addressId){
        return ResponseEntity.ok(addressService.getAddressByUserIdAndAddressId(userId, addressId));
    }

    //get zipcode data
    @GetMapping("/search-zipcode")
    public ResponseEntity<ApiResponseDTO> getZipcodeDataByCode(@RequestParam("zipcode") String code){
        return ResponseEntity.ok(zipcodeService.fetchZipCodeData(code));
    }

    @GetMapping("/validate/{userId}/{addressId}")
    public ResponseEntity<Boolean> validateAddress(@PathVariable("userId") Long userId, @PathVariable("addressId") Long addressId){
        return ResponseEntity.ok(true);
    }

}

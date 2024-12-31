package com.codewave.orderservice.service;

import com.codewave.orderservice.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8084", value = "ADDRESS-SERVICE")
public interface AddressClient {

    @GetMapping("api/address/user-address-ids")
    AddressDto getAddressByUserAndAddressIds(@RequestParam("userId") Long userId,
                                             @RequestParam("addressId") Long addressId);

}

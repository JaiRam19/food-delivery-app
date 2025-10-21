package com.codewave.orderservice.client;

import com.codewave.orderservice.exception.APIException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Service
@Slf4j
@RequiredArgsConstructor
public class AddressClient {

    private final RestTemplate restTemplate;

    @Value("${address.service.url}")
    private String addressServiceUrl;

    public Boolean isValidAddress(Long userId, Long addressId){

        String url = addressServiceUrl+"/validate/{userId}/{addressId}";
        try{
             return restTemplate.getForEntity(url, Boolean.class, userId, addressId).getBody();
        }catch (Exception e){
            throw new APIException(e.getMessage());
        }
    }
}

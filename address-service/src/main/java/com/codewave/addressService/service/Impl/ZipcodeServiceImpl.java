package com.codewave.addressService.service.Impl;

import com.codewave.addressService.dto.APIResponse;
import com.codewave.addressService.dto.zipcodeAPIResponse.ApiResponseDTO;
import com.codewave.addressService.service.ZipcodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class ZipcodeServiceImpl implements ZipcodeService {

    private WebClient webClient;

    @Override
    public ApiResponseDTO fetchZipCodeData(String codes) {
       return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("apikey", "bda46a90-c7fe-11ef-841c-9b321f93d482")
                        .queryParam("codes", codes)
                        .build())
                .retrieve()
                .bodyToMono(ApiResponseDTO.class)
                .block();
    }
}

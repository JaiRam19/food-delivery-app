package com.codewave.addressService.service;

import com.codewave.addressService.dto.zipcodeAPIResponse.ApiResponseDTO;

public interface ZipcodeService {
    ApiResponseDTO fetchZipCodeData(String codes);
}

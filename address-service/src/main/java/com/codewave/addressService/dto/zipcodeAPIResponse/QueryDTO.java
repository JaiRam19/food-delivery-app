package com.codewave.addressService.dto.zipcodeAPIResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QueryDTO {
    private List<String> codes;
    private String country;
}

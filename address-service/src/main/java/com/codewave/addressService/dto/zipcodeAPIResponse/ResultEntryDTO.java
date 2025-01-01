package com.codewave.addressService.dto.zipcodeAPIResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultEntryDTO {
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country_code")
    private String countryCode;
    private String latitude;
    private String longitude;
    private String city;
    private String state;

    @JsonProperty("city_en")
    private String cityEn;

    @JsonProperty("state_en")
    private String stateEn;

    @JsonProperty("state_code")
    private String stateCode;
    private String province;

    @JsonProperty("province_code")
    private String provinceCode;

}

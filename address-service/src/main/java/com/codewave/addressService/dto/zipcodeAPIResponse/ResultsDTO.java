package com.codewave.addressService.dto.zipcodeAPIResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultsDTO {
    private Map<String, List<ResultEntryDTO>> codes;
}

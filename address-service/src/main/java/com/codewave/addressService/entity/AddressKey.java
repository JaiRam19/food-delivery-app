package com.codewave.addressService.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AddressKey {
    private Long addressId;
    private Long userId;
}

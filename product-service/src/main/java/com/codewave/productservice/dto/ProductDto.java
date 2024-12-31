package com.codewave.productservice.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String productName;
    private String productCategory;
    private Long stock;
    private Double price;
    private LocalDate manufactureDate;
    private LocalDate expirationDate;
}

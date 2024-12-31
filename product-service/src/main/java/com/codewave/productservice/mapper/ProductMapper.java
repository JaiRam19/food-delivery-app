package com.codewave.productservice.mapper;

import com.codewave.productservice.dto.ProductDto;
import com.codewave.productservice.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public ProductDto mapToDto(Product product){
        return modelMapper.map(product, ProductDto.class);
    }

    public Product mapToEntity(ProductDto productDto){
        return modelMapper.map(productDto, Product.class);
    }
}

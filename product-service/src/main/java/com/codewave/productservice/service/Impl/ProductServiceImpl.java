package com.codewave.productservice.service.Impl;

import com.codewave.productservice.dto.ProductDto;
import com.codewave.productservice.entity.Product;
import com.codewave.productservice.exception.APIException;
import com.codewave.productservice.exception.ResourceNotFoundException;
import com.codewave.productservice.mapper.ProductMapper;
import com.codewave.productservice.repository.ProductRepository;
import com.codewave.productservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        //verify if the product is already exist
        if (productRepository.existsByProductName(productDto.getProductName())) {
            throw new APIException("Product is already present", HttpStatus.BAD_REQUEST);
        }

        //save the product into db
        Product savedProduct = productRepository.save(productMapper.mapToEntity(productDto));

        //convert and return it
        return productMapper.mapToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {

        //first verify if product is present or not
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", String.valueOf(productId)));

        //make sure product manufacturing date and expiration date should not modify
        if (!product.getExpirationDate().isEqual(productDto.getExpirationDate())) {
            throw new APIException("Product expiration date can't be changed", HttpStatus.BAD_REQUEST);
        }

        if (!product.getManufactureDate().equals(productDto.getManufactureDate())) {
            throw new APIException("Product manufacture date can't be changed", HttpStatus.BAD_REQUEST);
        }

        //set fields
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setProductCategory(productDto.getProductCategory());

        //save the updated product
        productRepository.save(product);

        return productMapper.mapToDto(product);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", String.valueOf(productId)));
        return productMapper.mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> productMapper.mapToDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteProductById(Long productId) {
        productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", String.valueOf(productId)));

        productRepository.deleteById(productId);
        return "Product " + productId + " deleted successfully";
    }

    //get the list of products from db
    //verify each product if it is already present or not by their name
    //if not add into database
    //finally return saved
    @Override
    public List<ProductDto> addMoreProducts(List<ProductDto> productDto) {
        //find all products from database and convert into list of strings(names)
        List<String> productNames = productRepository.findAll().stream()
                .map(Product::getProductName)
                .toList();

        //filter unique list
        List<Product> uniqueList = productDto.stream()
                .filter(product -> !productNames.contains(product.getProductName()))
                .map(uniqueProduct -> productMapper.mapToEntity(uniqueProduct))
                .toList();

        //save all unique list of products
        return productRepository.saveAll(uniqueList).stream()
                .map(product -> productMapper.mapToDto(product)).toList();
    }

    @Override
    public List<ProductDto> applyDiscount(int discountPercentage, String category, Long applicableQuantity) {
        //find all products form database and apply discount to eligible products
        List<Product> updatedList = productRepository.findAll().stream()
                .filter(product -> product.getProductCategory().equalsIgnoreCase(category) && product.getStock() > applicableQuantity)
                .peek(product -> product.setPrice(calculateDiscountPrice(product.getPrice(), discountPercentage)))
                .toList();

        //save updated products and convert into dto to return
        return productRepository.saveAll(updatedList).stream()
                .map(product -> productMapper.mapToDto(product))
                .toList();

    }

    @Override
    public List<ProductDto> applyFlatPriceReduction(String category, Double flatPrice) {
        return productRepository.findByProductCategory(category).stream()
                .peek(product -> product.setPrice(product.getPrice()-flatPrice))
                .map(product -> productMapper.mapToDto(product))
                .toList();
    }

    @Override
    public List<ProductDto> findProductsByCategory(String category) {
        return productRepository.findByProductCategory(category).stream()
                .map(product -> productMapper.mapToDto(product))
                .toList();
    }

    private Double calculateDiscountPrice(Double productPrice, int discountPercentage) {
        return productPrice - ((productPrice * discountPercentage)/ 100.0);
    }

}

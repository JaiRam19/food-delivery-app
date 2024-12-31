package com.codewave.productservice.controller;

import com.codewave.productservice.dto.ProductDto;
import com.codewave.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private ProductService productService;

    //add product
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.addProduct(productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    //update the product
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") Long productId,
                                                    @RequestBody ProductDto productDto) {
        ProductDto updateProduct = productService.updateProduct(productDto, productId);
        return ResponseEntity.ok(updateProduct);
    }

    //get product by id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    //get all products
    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //delete product by id
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.deleteProductById(productId));
    }

    //add list of products
    @PostMapping("/add-all")
    public ResponseEntity<List<ProductDto>> listOfProducts(@RequestBody List<ProductDto> listOfProducts){
        List<ProductDto> savedProducts = productService.addMoreProducts(listOfProducts);
        return new ResponseEntity<>(savedProducts, HttpStatus.CREATED);
    }

    //apply discount to products with given conditions
    @PutMapping("/apply-discount")
    public ResponseEntity<List<ProductDto>> applyDiscount(@RequestParam(name = "discountPercentage") int percentage,
                                                          @RequestParam(name = "category") String category,
                                                          @RequestParam(name = "eligibleQty") Long quantity){
        List<ProductDto> discountedProducts = productService.applyDiscount(percentage, category, quantity);
        return ResponseEntity.ok(discountedProducts);
    }

    //apply flat price
    @PutMapping("/apply-flatPrice")
    public ResponseEntity<List<ProductDto>> applyFlatPrice(@RequestParam("flatPrice") Double flatPrice,
                                                           @RequestParam("category") String category){
        List<ProductDto> flatPricedProducts = productService.applyFlatPriceReduction(category, flatPrice);
        return ResponseEntity.ok(flatPricedProducts);
    }

    //find products by category
    @GetMapping("/all-products/category/{category}")
    public ResponseEntity<List<ProductDto>> findByCategory(@PathVariable("category") String category){
        List<ProductDto> listOfProductsUnderGivenCategory = productService.findProductsByCategory(category);
        return ResponseEntity.ok(listOfProductsUnderGivenCategory);
    }

}

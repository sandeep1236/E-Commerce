package com.modmed.training.ecommerce.controller;

import com.modmed.training.ecommerce.dto.ProductDto;
import com.modmed.training.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Crud API for products", description = "This API allows you to manage products in the e-commerce application. You can create a new product by providing the necessary details such as product name, description, price, and other relevant information.")
public class ProductController {

    private final ProductService productService;


    @Operation(summary = "Create a new product", description = "This endpoint allows you to create a new product by providing the necessary details such as product name, description, price, and other relevant information.")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @PostMapping("/create")
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.saveProduct(productDto), HttpStatus.CREATED);
    }
}

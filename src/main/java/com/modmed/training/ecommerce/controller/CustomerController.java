package com.modmed.training.ecommerce.controller;

import com.modmed.training.ecommerce.dto.CustomerDto;
import com.modmed.training.ecommerce.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Crud API for customers", description = "This API allows you to manage customers in the e-commerce application. You can create a new customer by providing the necessary details.")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    @Operation(summary = "Create a new customer", description = "This endpoint allows you to create a new customer by providing the necessary details such as name, email, and address.")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }
}

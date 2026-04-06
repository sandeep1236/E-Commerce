package com.modmed.training.ecommerce.controller;

import com.modmed.training.ecommerce.dto.PaymentDto;
import com.modmed.training.ecommerce.service.PaymentService;
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
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Crud API for payments", description = "This API allows you to manage payments in the e-commerce application. You can create a new payment by providing the necessary details such as order information and payment method.")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    @Operation(summary = "Create a new payment", description = "This endpoint allows you to create a new payment by providing the necessary details such as order information and payment method.")
    @ApiResponse(responseCode = "201", description = "Payment created successfully")
    public ResponseEntity<PaymentDto> makePayment(@RequestBody PaymentDto paymentDto) {
        return new ResponseEntity<>(paymentService.makePayment(paymentDto), HttpStatus.CREATED);
    }
}

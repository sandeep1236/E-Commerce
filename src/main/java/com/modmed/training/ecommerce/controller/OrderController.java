package com.modmed.training.ecommerce.controller;

import com.modmed.training.ecommerce.dto.OrderDto;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import com.modmed.training.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Crud API for orders", description = "This API allows you to manage orders in the e-commerce application. You can create a new order, retrieve orders based on their status, and retrieve paginated orders for a specific customer.")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order", description = "This endpoint allows you to create a new order by providing the necessary details such as customer information and product details.")
    @ApiResponse(responseCode = "201", description = "Order created successfully")
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }

    @GetMapping("/spec")
    public ResponseEntity<List<OrderDto>> getOrderBySpec(@RequestParam String status) {
        return new ResponseEntity<>(orderService.getOrderBySpec(status), HttpStatus.OK);
    }

    @Operation(summary = "Get paginated orders for a specific customer", description = "This endpoint allows you to retrieve paginated orders for a specific customer by providing the customer ID and pagination details such as page number and page size.")
    @ApiResponse(responseCode = "200", description = "Paginated orders retrieved successfully")
    @GetMapping
        public ResponseEntity<Page<OrderDto>> getPageOrders(@RequestParam Long customerId, @ModelAttribute PaginationRequest paginationRequest){
        return new ResponseEntity<>(orderService.getOrders(customerId, paginationRequest), HttpStatus.OK);
    }
}

package com.modmed.training.ecommerce.controller;

import com.modmed.training.ecommerce.dto.CartDto;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import com.modmed.training.ecommerce.service.CartService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(
        name = "Crud Api for cart",
        description = "This API allows you to manage the cart items in the e-commerce application. You can add items to the cart and retrieve the list of cart items based on their visibility status."
)
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "Add item to cart",
            description = "This endpoint allows you to add an item to the cart. You need to"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Item added to cart successfully"
    )
    @PostMapping("/add-to-cart")
    public ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.addToCart(cartDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get the cart items",
            description = "This endpoint allows you to retrieve the list of cart items based on their visibility"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of cart items retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<Page<CartDto>> getCartItems(@RequestParam Boolean isVisible, @ModelAttribute PaginationRequest paginationRequest) {
        return new ResponseEntity<>(cartService.getCartItems(isVisible, paginationRequest), HttpStatus.OK);
    }
}

package com.modmed.training.ecommerce.service;

import com.modmed.training.ecommerce.dto.CartDto;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import org.springframework.data.domain.Page;


public interface CartService {
    CartDto addToCart(CartDto cartDto);
    Page<CartDto> getCartItems(Boolean isVisible, PaginationRequest paginationRequest);
}

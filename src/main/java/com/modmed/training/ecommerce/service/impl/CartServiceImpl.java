package com.modmed.training.ecommerce.service.impl;

import com.modmed.training.ecommerce.mappers.CartMapper;
import com.modmed.training.ecommerce.dto.CartDto;
import com.modmed.training.ecommerce.exception.CustomerNotFoundException;
import com.modmed.training.ecommerce.model.Cart;
import com.modmed.training.ecommerce.model.Customer;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import com.modmed.training.ecommerce.repo.CartRepo;
import com.modmed.training.ecommerce.repo.CustomerRepo;
import com.modmed.training.ecommerce.service.CartService;
import com.modmed.training.ecommerce.specification.cart.CartSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final CustomerRepo customerRepo;

    @Override
    public CartDto addToCart(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }

        Customer customer = customerRepo.findById(cartDto.getCustomer().getId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        Cart cart = CartMapper.INSTANCE.toEntity(cartDto);
        if (cartDto.getId() != null) {
            Cart existingCartItem = cartRepo.findById(cartDto.getId()).orElseThrow(() -> new RuntimeException("Item not present in the cart with the given id: " + cartDto.getId()));
            cart.setDateCreated(existingCartItem.getDateCreated());
            cart.setCreatedById(existingCartItem.getCreatedById());
        }
        if (cart.getIsVisible() == null) {
            cart.setIsVisible(Boolean.TRUE);
        }

        if (cart.getQuantity() == 0) {
            cart.setIsVisible(Boolean.FALSE);
        }
        cart.setCustomer(customer);
        cart.setProductId(cartDto.getProductId());
        return CartMapper.INSTANCE.toDto(cartRepo.save(cart));
    }

    @Override
    public Page<CartDto> getCartItems(Boolean isVisible, PaginationRequest paginationRequest) {
        final PageRequest pageRequest = PageRequest.of(paginationRequest.getPage(), paginationRequest.getPageSize(), Sort.by(paginationRequest.getDirection(), paginationRequest.getSortBy()));
        final Page<Cart> cartItems = cartRepo.findAll(CartSpecification.isActiveCartItems(isVisible), pageRequest);
        return cartItems.map(CartMapper.INSTANCE::toDto);
    }
}

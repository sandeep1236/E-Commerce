package com.modmed.training.ecommerce.service;

import com.modmed.training.ecommerce.dto.OrderDto;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getOrderBySpec(String status);

    List<OrderDto> getOrders(Long customerId);
    Page<OrderDto> getOrders(Long customerId, PaginationRequest paginationRequest);
}

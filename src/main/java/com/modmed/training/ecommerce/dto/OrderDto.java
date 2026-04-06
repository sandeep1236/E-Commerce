package com.modmed.training.ecommerce.dto;

import com.modmed.training.ecommerce.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private CustomerDto customer;
    private List<ProductDto> products;
    private double quantity;
    private OrderStatus status;
    private Date orderDate;
    private double totalPrice;
}

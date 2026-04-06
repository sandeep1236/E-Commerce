package com.modmed.training.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private Long id;
    private CustomerDto customer;
    private String productId;
    private Double quantity;
    private Boolean isVisible;
}

package com.modmed.training.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String productId;
    private String name;
    private String description;
    private Double price;
    private Double quantity;
}

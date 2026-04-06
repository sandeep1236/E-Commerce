package com.modmed.training.ecommerce.dto;

import com.modmed.training.ecommerce.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInventoryDto {
    private Long id;
    private String productId;
    private String name;
    private String description;
    private Double price;
    private Double quantity;
    private ProductStatus status;
    private List<FileAttachmentDto> fileAttachment;
}

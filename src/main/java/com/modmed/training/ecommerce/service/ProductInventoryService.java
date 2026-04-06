package com.modmed.training.ecommerce.service;

import com.modmed.training.ecommerce.dto.ProductInventoryDto;
import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInventoryService {
    List<ProductInventoryDto> save(List<ProductInventoryDto> productInventoryDto);
    Page<ProductInventory> getProducts(PaginationRequest paginationRequest);
    Page<ProductInventory> searchProducts(PaginationRequest paginationRequest, String searchTerm);
}

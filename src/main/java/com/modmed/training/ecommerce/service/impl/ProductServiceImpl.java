package com.modmed.training.ecommerce.service.impl;

import com.modmed.training.ecommerce.mappers.ProductMapper;
import com.modmed.training.ecommerce.dto.ProductDto;
import com.modmed.training.ecommerce.repo.ProductRepo;
import com.modmed.training.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;


    @Override
    public ProductDto saveProduct(ProductDto product) {
        return ProductMapper.INSTANCE.toDto(productRepo.save(ProductMapper.INSTANCE.toEntity(product)));
    }
}

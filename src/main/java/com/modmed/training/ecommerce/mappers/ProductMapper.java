package com.modmed.training.ecommerce.mappers;

import com.modmed.training.ecommerce.dto.ProductDto;
import com.modmed.training.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public ProductDto toDto(Product product);

    public Product toEntity(ProductDto productDto);

    public List<ProductDto> toDtoList(List<Product> products);

    public List<Product> toEntityList(List<ProductDto> productDto);

}

package com.modmed.training.ecommerce.mappers;

import com.modmed.training.ecommerce.dto.ProductInventoryDto;
import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductInventoryMapper {
    ProductInventoryMapper INSTANCE = Mappers.getMapper(ProductInventoryMapper.class);

    ProductInventory toEntity(ProductInventoryDto productInventoryDto);
    ProductInventoryDto toDto(ProductInventory productInventory);

    List<ProductInventoryDto> toDtoList(List<ProductInventory> productInventoryList);
    List<ProductInventory> toEntityList(List<ProductInventoryDto> productInventoryDtoList);
}

package com.modmed.training.ecommerce.mappers;

import com.modmed.training.ecommerce.dto.CartDto;
import com.modmed.training.ecommerce.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDto toDto(Cart cart);
    Cart toEntity(CartDto cartDto);
    List<CartDto> toDtoList(List<Cart> cartItems);
    List<Cart> toEntityList(List<CartDto> cartItemsDto);
}

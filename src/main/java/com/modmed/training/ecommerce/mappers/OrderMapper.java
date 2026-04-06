package com.modmed.training.ecommerce.mappers;

import com.modmed.training.ecommerce.dto.CustomerDto;
import com.modmed.training.ecommerce.dto.OrderDto;
import com.modmed.training.ecommerce.dto.ProductDto;
import com.modmed.training.ecommerce.model.Customer;
import com.modmed.training.ecommerce.model.Order;
import com.modmed.training.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);


    public OrderDto toDto(Order order);

    public Order toEntity(OrderDto orderDto);

    public List<OrderDto> toListDto(List<Order> orders);

    CustomerDto toCustomerDto(Customer customer);

    // Map Product List → ProductDTO List
    List<ProductDto> toProductDtoList(List<Product> products);

    // Map ProductDTO List → Product List
    List<Product> toProductList(List<ProductDto> productDTOs);

}

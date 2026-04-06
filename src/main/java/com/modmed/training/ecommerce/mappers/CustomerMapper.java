package com.modmed.training.ecommerce.mappers;

import com.modmed.training.ecommerce.dto.CustomerDto;
import com.modmed.training.ecommerce.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public Customer toEntity(CustomerDto customerDto);
    public CustomerDto toDto(Customer customer);
}

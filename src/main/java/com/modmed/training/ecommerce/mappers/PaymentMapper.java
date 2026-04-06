package com.modmed.training.ecommerce.mappers;

import com.modmed.training.ecommerce.dto.PaymentDto;
import com.modmed.training.ecommerce.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    public PaymentDto toDto(Payment payment);
    public Payment toEntity(PaymentDto paymentDto);
}

package com.modmed.training.ecommerce.dto;


import com.modmed.training.ecommerce.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentDto {
    private CustomerDto customer;
    private OrderDto order;
    private Date paymentDate;
    private PaymentStatus status;
}

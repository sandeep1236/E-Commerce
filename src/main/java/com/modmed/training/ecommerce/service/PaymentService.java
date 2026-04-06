package com.modmed.training.ecommerce.service;

import com.modmed.training.ecommerce.dto.PaymentDto;

public interface PaymentService {
    PaymentDto makePayment(PaymentDto paymentDto);
}

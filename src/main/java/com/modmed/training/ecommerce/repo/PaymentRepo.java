package com.modmed.training.ecommerce.repo;

import com.modmed.training.ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}

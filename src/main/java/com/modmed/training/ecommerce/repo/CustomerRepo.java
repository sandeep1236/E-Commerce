package com.modmed.training.ecommerce.repo;

import com.modmed.training.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}

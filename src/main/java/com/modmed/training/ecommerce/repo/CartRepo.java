package com.modmed.training.ecommerce.repo;

import com.modmed.training.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CartRepo extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
}

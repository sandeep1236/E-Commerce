package com.modmed.training.ecommerce.repo;

import com.modmed.training.ecommerce.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @EntityGraph(attributePaths = {"customer", "products"})
    Page<Order> findAll(Specification<Order> spec, Pageable pageable);
}

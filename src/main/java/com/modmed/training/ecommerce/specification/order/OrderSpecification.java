package com.modmed.training.ecommerce.specification.order;

import com.modmed.training.ecommerce.model.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    private OrderSpecification() {
        /* This utility class should not be instantiated */
    }

    public static Specification<Order> hasStatus(String status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Order> withCustomerId(Long customerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("customer").get("id"), customerId);
    }
}

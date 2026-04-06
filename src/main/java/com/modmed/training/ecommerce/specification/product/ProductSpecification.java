package com.modmed.training.ecommerce.specification.product;

import com.modmed.training.ecommerce.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    private ProductSpecification() {
        /* This utility class should not be instantiated */
    }


    public static Specification<Product> hasProductId(String productId) {
        return (root, query, cb) -> cb.equal(root.get("productId"), productId);
    }
}

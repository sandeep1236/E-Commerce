package com.modmed.training.ecommerce.specification.productInventory;

import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductInventorySpec {
    private ProductInventorySpec() {
        /* This utility class should not be instantiated */
    }


    public static Specification<ProductInventory> hasProductIds(List<String> productIds) {
        return (root, query, cb) -> root.get("productId").in(productIds);
    }

    public static Specification<ProductInventory> hasProductId(String productId) {
        return (root, query, cb) -> cb.equal(root.get("productId"), productId);
    }

    public static Specification<ProductInventory> search(String searchTerm) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%");
    }
}

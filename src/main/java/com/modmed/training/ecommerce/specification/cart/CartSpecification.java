package com.modmed.training.ecommerce.specification.cart;

import com.modmed.training.ecommerce.model.Cart;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CartSpecification {

    private CartSpecification() {}

    public static Specification<Cart> isActiveCartItems(Boolean status) {
        return (root, query, cb) -> cb.equal(root.get("isVisible"), status);
    }

    public static Specification<Cart> withProductId(List<Long> ids) {
        return (root, query, cb) -> root.get("id").in(ids);
    }

    public static Specification<Cart> withProductUUID(List<String> ids) {
        return (root, query, cb) -> root.get("productId").in(ids);
    }

    public static Specification<Cart> withCustomerId(Long customerId){
        return (root, query,cb)-> cb.equal(root.join("customer").get("id"), customerId);
    }
}

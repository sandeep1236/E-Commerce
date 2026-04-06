package com.modmed.training.ecommerce.repo;

import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductInventoryRepo extends JpaRepository<ProductInventory, Long>, JpaSpecificationExecutor<ProductInventory> {
}

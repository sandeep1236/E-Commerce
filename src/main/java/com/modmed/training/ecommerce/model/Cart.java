package com.modmed.training.ecommerce.model;

import com.modmed.training.ecommerce.model.audit.BaseEntityAudit;
import com.modmed.training.ecommerce.model.audit.CustomerAware;
import com.modmed.training.ecommerce.model.audit.Listeners.BaseEntityAuditListener;
import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_id", "product_id"})})
@EntityListeners(BaseEntityAuditListener.class)
public class Cart extends BaseEntityAudit implements CustomerAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    private String productId;
    private Double quantity;
    private Boolean isVisible;
}

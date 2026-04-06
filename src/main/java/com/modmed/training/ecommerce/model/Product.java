package com.modmed.training.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modmed.training.ecommerce.model.audit.BaseEntityAudit;
import com.modmed.training.ecommerce.model.audit.CustomerAware;
import com.modmed.training.ecommerce.model.audit.Listeners.BaseEntityAuditListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@EntityListeners(BaseEntityAuditListener.class)
public class Product extends BaseEntityAudit implements CustomerAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private String name;
    private String description;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    private Double quantity;
}

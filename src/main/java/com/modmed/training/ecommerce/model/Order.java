package com.modmed.training.ecommerce.model;

import com.modmed.training.ecommerce.model.audit.BaseEntityAudit;
import com.modmed.training.ecommerce.model.audit.CustomerAware;
import com.modmed.training.ecommerce.model.audit.Listeners.BaseEntityAuditListener;
import com.modmed.training.ecommerce.model.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
@EntityListeners(BaseEntityAuditListener.class)
public class Order extends BaseEntityAudit implements CustomerAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @OneToMany(mappedBy ="order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
    private double quantity;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private double totalPrice;
    private Date orderDate;
}

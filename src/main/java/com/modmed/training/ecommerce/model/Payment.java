package com.modmed.training.ecommerce.model;

import com.modmed.training.ecommerce.model.audit.BaseEntityAudit;
import com.modmed.training.ecommerce.model.audit.CustomerAware;
import com.modmed.training.ecommerce.model.audit.Listeners.BaseEntityAuditListener;
import com.modmed.training.ecommerce.model.enums.PaymentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(BaseEntityAuditListener.class)
public class Payment extends BaseEntityAudit implements CustomerAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
    private Double amount;
    private Date paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}

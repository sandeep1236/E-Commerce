package com.modmed.training.ecommerce.model.audit;

import com.modmed.training.ecommerce.model.Customer;

/**
 * Interface for entities that have a customer field.
 * Used by the audit listener to set createdById/modifiedById.
 */
public interface CustomerAware {
    Customer getCustomer();
}


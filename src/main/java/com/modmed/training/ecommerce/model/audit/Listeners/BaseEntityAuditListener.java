package com.modmed.training.ecommerce.model.audit.Listeners;


import com.modmed.training.ecommerce.model.audit.BaseEntityAudit;
import com.modmed.training.ecommerce.model.audit.CustomerAware;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Date;


public class BaseEntityAuditListener {


    @PrePersist
    public void onPreUpdate(Object entity) {
        if(entity instanceof BaseEntityAudit baseEntityAudit){
            baseEntityAudit.setDateCreated(new Date());
            if (entity instanceof CustomerAware customerAware) {
                baseEntityAudit.setCreatedById(customerAware.getCustomer());
            }
        }

    }

    @PreUpdate
    public void onPostUpdate(Object entity) {
        if(entity instanceof BaseEntityAudit baseEntityAudit){
            baseEntityAudit.setDateModified(new Date());
            if (entity instanceof CustomerAware customerAware) {
                baseEntityAudit.setModifiedById(customerAware.getCustomer());
            }
        }

    }
}

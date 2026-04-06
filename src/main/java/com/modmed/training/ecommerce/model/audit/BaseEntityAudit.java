package com.modmed.training.ecommerce.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modmed.training.ecommerce.model.Customer;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
@JsonIgnoreProperties({"dateCreated", "dateModified", "createdById", "modifiedById"})
public class BaseEntityAudit {
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Customer createdById;
    @ManyToOne
    @JoinColumn(name = "modified_by_id")
    private Customer modifiedById;

}

package com.modmed.training.ecommerce.model.productInventory;

import com.modmed.training.ecommerce.model.enums.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private String name;
    private String description;
    private Double price;
    private Double quantity;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_inventory_file",
            joinColumns = @JoinColumn(name = "product_inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "file_attachment_id")
    )
    private List<FileAttachment> fileAttachment;
}

package com.modmed.training.ecommerce.repo;

import com.modmed.training.ecommerce.model.productInventory.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentRepo extends JpaRepository<FileAttachment,Long> {
}

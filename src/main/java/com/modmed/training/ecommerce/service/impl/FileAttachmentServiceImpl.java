package com.modmed.training.ecommerce.service.impl;


import com.modmed.training.ecommerce.model.productInventory.FileAttachment;
import com.modmed.training.ecommerce.repo.FileAttachmentRepo;
import com.modmed.training.ecommerce.service.FileAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileAttachmentServiceImpl implements FileAttachmentService {
    @Value("${ecommerce.file.storage-dir:uploads}")
    private String storageDir;
    private final FileAttachmentRepo fileAttachmentRepo;

    @Override
    public void saveFiles(List<MultipartFile> file) {
        List<FileAttachment> fileAttachments = new ArrayList<>();
        try {
            final Path storagePath = Paths.get(storageDir);
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }
            for (MultipartFile multipartFile : file) {
                String uniqueName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
                Path targetPath = storagePath.resolve(uniqueName);
                Files.copy(multipartFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                FileAttachment fileAttachment = new FileAttachment();
                fileAttachment.setFileName(uniqueName);
                fileAttachment.setFilePath(targetPath.toString());
                fileAttachment.setSize(String.valueOf(multipartFile.getSize()));
                fileAttachment.setFileType(multipartFile.getContentType());
                fileAttachments.add(fileAttachment);
            }
            fileAttachmentRepo.saveAll(fileAttachments);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save files", e);
        }

    }
}

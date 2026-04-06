package com.modmed.training.ecommerce.service;


import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileAttachmentService {
    void saveFiles(List<MultipartFile> file);
}

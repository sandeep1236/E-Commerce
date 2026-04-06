package com.modmed.training.ecommerce.controller;

import com.modmed.training.ecommerce.service.FileAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/file-attachments")
@RequiredArgsConstructor
public class FileAttachmentController {

    private final FileAttachmentService fileAttachmentService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFiles(@RequestBody List<MultipartFile> files) {
        fileAttachmentService.saveFiles(files);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

package com.modmed.training.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachmentDto {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private String size;
}

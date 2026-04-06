package com.modmed.training.ecommerce.mappers;

import com.modmed.training.ecommerce.dto.FileAttachmentDto;
import com.modmed.training.ecommerce.model.productInventory.FileAttachment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileAttachmentMapper {
        FileAttachmentDto toDto(FileAttachment fileAttachment);
        FileAttachment toEntity(FileAttachmentDto fileAttachmentDto);
        List<FileAttachmentDto> toListDto(List<FileAttachment> fileAttachments);
        List<FileAttachment> toListEntity(List<FileAttachmentDto> fileAttachmentDtos);
}

package com.modmed.training.ecommerce.service.impl;

import com.modmed.training.ecommerce.dto.FileAttachmentDto;
import com.modmed.training.ecommerce.mappers.ProductInventoryMapper;
import com.modmed.training.ecommerce.dto.ProductInventoryDto;
import com.modmed.training.ecommerce.model.productInventory.FileAttachment;
import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import com.modmed.training.ecommerce.repo.FileAttachmentRepo;
import com.modmed.training.ecommerce.repo.ProductInventoryRepo;
import com.modmed.training.ecommerce.service.ProductInventoryService;
import com.modmed.training.ecommerce.specification.productInventory.ProductInventorySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final ProductInventoryRepo productInventoryRepo;
    private final FileAttachmentRepo fileAttachmentRepo;


    @Override
    public List<ProductInventoryDto> save(List<ProductInventoryDto> productInventoryDto) {
        List<ProductInventory> productInventoryItems = ProductInventoryMapper.INSTANCE.toEntityList(productInventoryDto);
        List<Long> fileAttachmentIds = productInventoryDto.stream()
                .map(ProductInventoryDto::getFileAttachment)
                .filter(Objects::nonNull)
                .flatMap(fileAttachmentDtos -> fileAttachmentDtos.stream().map(FileAttachmentDto::getId))
                .filter(Objects::nonNull)
                .toList();

        final Map<Long, FileAttachment> fileAttachmentsMap = fileAttachmentRepo.findAllById(fileAttachmentIds)
                .stream().collect(Collectors.toMap(FileAttachment::getId, Function.identity()));


        List<ProductInventory> newProducts;
        List<ProductInventory> existingProducts;

        newProducts = productInventoryItems.stream().filter(productInventory -> productInventory.getId() == null).collect(Collectors.toList());
        existingProducts = productInventoryItems.stream().filter(productInventory -> productInventory.getId() != null).toList();

        newProducts.forEach(productInventory -> {
            productInventory.setProductId(UUID.randomUUID().toString());
            if (productInventory.getFileAttachment() != null && !productInventory.getFileAttachment().isEmpty()) {
                final List<FileAttachment> finalFileAttachments = productInventory.getFileAttachment().stream()
                        .map(fileAttachment -> fileAttachmentsMap.get(fileAttachment.getId())).toList();
                productInventory.setFileAttachment(finalFileAttachments);
            }
        });

        if (!existingProducts.isEmpty()) {
            updateProductInventory(existingProducts, fileAttachmentsMap);
            newProducts.addAll(existingProducts);
        }


        return ProductInventoryMapper.INSTANCE.toDtoList(productInventoryRepo.saveAll(newProducts));
    }

    private void updateProductInventory(List<ProductInventory> existingProducts, Map<Long, FileAttachment> fileAttachmentsMap) {
        Map<Long, ProductInventory> productIdMap = productInventoryRepo.findAllById(existingProducts.stream()
                        .map(ProductInventory::getId).toList()).
                stream().collect(Collectors.toMap(ProductInventory::getId, Function.identity()));
        existingProducts.forEach(existingProduct -> {
            existingProduct.setProductId( existingProduct.getProductId() !=null ? existingProduct.getProductId(): productIdMap.get(existingProduct.getId()).getProductId());
            existingProduct.setName(existingProduct.getName() !=null ? existingProduct.getName():productIdMap.get(existingProduct.getId()).getName());
            existingProduct.setStatus(existingProduct.getStatus() !=null ? existingProduct.getStatus():productIdMap.get(existingProduct.getId()).getStatus());
            existingProduct.setDescription(existingProduct.getDescription() !=null ? existingProduct.getDescription():productIdMap.get(existingProduct.getId()).getDescription());
            existingProduct.setPrice(existingProduct.getPrice() !=null ? existingProduct.getPrice():productIdMap.get(existingProduct.getId()).getPrice());
            Double newQuantity = existingProduct.getQuantity() + productIdMap.get(existingProduct.getId()).getQuantity();
            if (existingProduct.getFileAttachment() != null && !existingProduct.getFileAttachment().isEmpty()) {
                final List<FileAttachment> finalFileAttachments = existingProduct.getFileAttachment()
                        .stream().map(fileAttachment -> fileAttachmentsMap.get(fileAttachment.getId())).toList();
                existingProduct.setFileAttachment(finalFileAttachments);
            }
            existingProduct.setQuantity(newQuantity);
        });
    }

    //Need to add pagination in the future
    @Override
    public Page<ProductInventory> getProducts(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getPageSize(), Sort.by(paginationRequest.getDirection(), paginationRequest.getSortBy()));
        return productInventoryRepo.findAll(pageable);
    }

    @Override
    public Page<ProductInventory> searchProducts(PaginationRequest paginationRequest, String searchTerm) {
        Pageable pageRequest = PageRequest.of(paginationRequest.getPage(),
                paginationRequest.getPageSize(), Sort.by(paginationRequest.getDirection(), paginationRequest.getSortBy()));
        Specification<ProductInventory> searchSpec = ProductInventorySpec.search(searchTerm);
        return productInventoryRepo.findAll(searchSpec, pageRequest);
    }
}

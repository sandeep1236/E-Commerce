package com.modmed.training.ecommerce.controller;

import com.modmed.training.ecommerce.dto.ProductInventoryDto;
import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import com.modmed.training.ecommerce.service.ProductInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product-inventory")
@RequiredArgsConstructor
@Tag(name = "Crud API for product inventory", description = "This API allows you to manage product inventory in the e-commerce application. You can add new product inventory, retrieve paginated product inventory, and search for products in the inventory.")
public class ProductInventoryController {

    private final ProductInventoryService productInventoryService;

    @Operation(summary = "Add new product inventory", description = "This endpoint allows you to add new product inventory by providing the necessary details such as product information and quantity.")
    @ApiResponse(responseCode = "201", description = "Product inventory added successfully")
    @PostMapping("/save")
    public ResponseEntity<List<ProductInventoryDto>> addProductInventory(@RequestBody List<ProductInventoryDto> productInventoryDto) {
        return new ResponseEntity<>(productInventoryService.save(productInventoryDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get paginated product inventory", description = "This endpoint allows you to retrieve paginated product inventory by providing pagination details such as page number and page size.")
    @ApiResponse(responseCode = "200", description = "Paginated product inventory retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<ProductInventory>> getProducts(@ModelAttribute PaginationRequest paginationRequest){
        return new ResponseEntity<>(productInventoryService.getProducts(paginationRequest), HttpStatus.OK);
    }

    //Need to have one search endpoint so that user can search for the product that they want to place the order

    @Operation(summary = "Search for products in inventory", description = "This endpoint allows you to search for products in the inventory by providing a search term and pagination details such as page number and page size.")
    @ApiResponse(responseCode = "200", description = "Products searched successfully")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductInventory>> searchProducts(@ModelAttribute PaginationRequest paginationRequest, @RequestParam String searchTerm) {
        return new ResponseEntity<>(productInventoryService.searchProducts(paginationRequest, searchTerm), HttpStatus.OK);
    }
}

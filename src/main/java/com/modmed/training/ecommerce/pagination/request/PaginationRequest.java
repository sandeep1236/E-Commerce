package com.modmed.training.ecommerce.pagination.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {
    private Integer page = 0;
    private Integer pageSize = 10;
    private String sortBy = "id";
    private Sort.Direction direction = Sort.Direction.ASC;
}


package com.modmed.training.ecommerce.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RuntimeExceptionDto {
    private String statusCode;
    private String errorMessage;
}

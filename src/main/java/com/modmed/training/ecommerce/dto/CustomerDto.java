package com.modmed.training.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties({"password"})
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
}

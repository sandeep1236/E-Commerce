package com.modmed.training.ecommerce.service.impl;

import com.modmed.training.ecommerce.mappers.CustomerMapper;
import com.modmed.training.ecommerce.dto.CustomerDto;
import com.modmed.training.ecommerce.model.Customer;
import com.modmed.training.ecommerce.repo.CustomerRepo;
import com.modmed.training.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.INSTANCE.toEntity(customerDto);
        return CustomerMapper.INSTANCE.toDto(customerRepo.save(customer));
    }
}

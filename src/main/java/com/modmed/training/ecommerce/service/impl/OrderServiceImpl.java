package com.modmed.training.ecommerce.service.impl;

import com.modmed.training.ecommerce.dto.ProductDto;
import com.modmed.training.ecommerce.mappers.OrderMapper;
import com.modmed.training.ecommerce.dto.CustomerDto;
import com.modmed.training.ecommerce.dto.OrderDto;
import com.modmed.training.ecommerce.model.Customer;
import com.modmed.training.ecommerce.model.Order;
import com.modmed.training.ecommerce.model.Product;
import com.modmed.training.ecommerce.model.enums.OrderStatus;
import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import com.modmed.training.ecommerce.pagination.request.PaginationRequest;
import com.modmed.training.ecommerce.repo.CustomerRepo;
import com.modmed.training.ecommerce.repo.OrderRepo;
import com.modmed.training.ecommerce.repo.ProductInventoryRepo;
import com.modmed.training.ecommerce.service.OrderService;
import com.modmed.training.ecommerce.specification.order.OrderSpecification;
import com.modmed.training.ecommerce.specification.productInventory.ProductInventorySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ProductInventoryRepo productInventoryRepo;


    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        validateOrder(orderDto);
        Order order = OrderMapper.INSTANCE.toEntity(orderDto);
        CustomerDto customerDto = orderDto.getCustomer();
        if (customerDto.getId() != null) {
            Customer customer = customerRepo.findById(customerDto.getId()).orElse(null);
            order.setCustomer(customer);
        }

        final List<String> productIds = orderDto.getProducts().stream().map(ProductDto::getProductId).toList();
        final Map<String, Double> productQuantityMap = orderDto.getProducts().stream().collect(Collectors.toMap(ProductDto::getProductId, ProductDto::getQuantity));
        final List<Product> products = buildProducts(productIds, productQuantityMap);
        Double totalAmount = products.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
        Integer totalItems = products.stream().mapToInt(product -> product.getQuantity().intValue()).sum();
        products.forEach(product -> {
            product.setOrder(order);
            product.setCustomer(order.getCustomer());
        });
        order.setProducts(products);
        order.setStatus(OrderStatus.CREATED);
        order.setOrderDate(new Date());
        order.setTotalPrice(totalAmount);
        order.setQuantity(totalItems);
        return OrderMapper.INSTANCE.toDto(orderRepo.save(order));
    }

    private List<Product> buildProducts(List<String> productIds, Map<String, Double> productQuantityMap) {
        List<Product> products = new ArrayList<>();
        final List<ProductInventory> inventoryProducts = productInventoryRepo.findAll(ProductInventorySpec.hasProductIds(productIds));
        for (ProductInventory inventoryProduct : inventoryProducts) {
            products.add(Product.builder()
                    .productId(inventoryProduct.getProductId())
                    .name(inventoryProduct.getName())
                    .description(inventoryProduct.getDescription())
                    .quantity(productQuantityMap.get(inventoryProduct.getProductId()))
                    .price(inventoryProduct.getPrice())
                    .build());
        }
        return products;
    }

    private void validateOrder(OrderDto orderDto) {
        if(orderDto.getCustomer().getId() == null){
            throw new IllegalArgumentException("Customer ID is required");
        }
        final boolean anyNullProduct = orderDto.getProducts().stream().anyMatch(product -> product.getProductId() == null);
        if(anyNullProduct){
            throw new IllegalArgumentException("All products must have an ID");
        }
    }

    @Override
    public List<OrderDto> getOrderBySpec(String status) {
        Specification<Order> spec = OrderSpecification.hasStatus(status);
        return OrderMapper.INSTANCE.toListDto(orderRepo.findAll(spec));
    }

    @Override
    public List<OrderDto> getOrders(Long customerId) {
        return OrderMapper.INSTANCE.toListDto(orderRepo.findAll(OrderSpecification.withCustomerId(customerId).and(OrderSpecification.hasStatus("PLACED"))));
    }

    @Override
    public Page<OrderDto> getOrders(Long customerId, PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize(), Sort.by(paginationRequest.getDirection(), paginationRequest.getSortBy()));
        final Page<Order> orders = orderRepo.findAll(OrderSpecification.withCustomerId(customerId), pageable);
        return orders.map(OrderMapper.INSTANCE::toDto);
    }
}

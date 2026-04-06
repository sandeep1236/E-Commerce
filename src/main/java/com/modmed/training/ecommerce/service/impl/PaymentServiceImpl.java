package com.modmed.training.ecommerce.service.impl;

import com.modmed.training.ecommerce.mappers.PaymentMapper;
import com.modmed.training.ecommerce.mappers.ProductMapper;
import com.modmed.training.ecommerce.dto.CustomerDto;
import com.modmed.training.ecommerce.dto.PaymentDto;
import com.modmed.training.ecommerce.dto.ProductDto;
import com.modmed.training.ecommerce.exception.CustomerNotFoundException;
import com.modmed.training.ecommerce.exception.ProductOutOfStockException;
import com.modmed.training.ecommerce.model.Cart;
import com.modmed.training.ecommerce.model.Customer;
import com.modmed.training.ecommerce.model.Order;
import com.modmed.training.ecommerce.model.Payment;
import com.modmed.training.ecommerce.model.Product;
import com.modmed.training.ecommerce.model.productInventory.ProductInventory;
import com.modmed.training.ecommerce.model.enums.OrderStatus;
import com.modmed.training.ecommerce.model.enums.PaymentStatus;
import com.modmed.training.ecommerce.model.enums.ProductStatus;
import com.modmed.training.ecommerce.repo.CartRepo;
import com.modmed.training.ecommerce.repo.CustomerRepo;
import com.modmed.training.ecommerce.repo.OrderRepo;
import com.modmed.training.ecommerce.repo.PaymentRepo;
import com.modmed.training.ecommerce.repo.ProductInventoryRepo;
import com.modmed.training.ecommerce.service.PaymentService;
import com.modmed.training.ecommerce.specification.cart.CartSpecification;
import com.modmed.training.ecommerce.specification.productInventory.ProductInventorySpec;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepo paymentRepo;
    private final CustomerRepo customerRepo;
    private final ProductInventoryRepo productInventoryRepo;
    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;

    /* Need to check if item present in the cart and remove it on successful payment*/
    @Override
    public PaymentDto makePayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.INSTANCE.toEntity(paymentDto);
        Customer customer = customerRepo.findById(paymentDto.getCustomer().getId()).orElseThrow(() -> new CustomerNotFoundException("customer not found"));

        Order order = orderRepo.findById(paymentDto.getOrder().getId()).orElseThrow(() -> new RuntimeException("Make payment for valid order"));
        Double grandTotal = order.getProducts().stream().map(Product::getPrice).mapToDouble(Double::doubleValue).sum();


        order.setTotalPrice(grandTotal);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.SHIPPED);

        payment.setCustomer(customer);
        payment.setOrder(order);
        payment.setAmount(grandTotal);
        payment.setPaymentDate(new Date());
        payment.setStatus(PaymentStatus.SUCCESS);

        PaymentDto dto = Optional.ofNullable(PaymentMapper.INSTANCE.toDto(paymentRepo.save(payment))).orElse(null);
        if (dto != null) {
            updateProductInventory(ProductMapper.INSTANCE.toEntityList(dto.getOrder().getProducts()));
            updateCartItems(dto.getOrder().getProducts(), dto.getCustomer());
        }

        return dto;
    }

    private void updateCartItems(List<ProductDto> products, CustomerDto customerDto) {
        Specification<Cart> cartSpecification = CartSpecification.withProductUUID(products.stream().map(ProductDto::getProductId).toList()).and(CartSpecification.withCustomerId(customerDto.getId()));
        List<Cart> cartItems = cartRepo.findAll(cartSpecification);
        cartItems.stream().forEach(cart -> cart.setIsVisible(false));
        cartRepo.saveAll(cartItems);
    }

    private void updateProductInventory(List<Product> products) {
        Specification<ProductInventory> spec = ProductInventorySpec.hasProductIds(products.stream().map(Product::getProductId).toList());
        List<ProductInventory> productInventoryItems = productInventoryRepo.findAll(spec);
        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));

        productInventoryItems.forEach(productInventory -> {
            if (productMap.containsKey(productInventory.getProductId())) {
                double updatedQuantity = productInventory.getQuantity() - productMap.get(productInventory.getProductId()).getQuantity();
                if (updatedQuantity < 0 || productInventory.getStatus().equals(ProductStatus.OUT_OF_STOCK)) {

                    throw new ProductOutOfStockException("Ordered " + productMap.get(productInventory.getProductId()).getQuantity() + ", but the stock is " + productInventory.getQuantity() + " ,product might be out of stock");
                } else {
                    productInventory.setQuantity(updatedQuantity);
                    if (productInventory.getQuantity() == 0) {
                        productInventory.setStatus(ProductStatus.OUT_OF_STOCK);
                    }
                }
            }
        });
        productInventoryRepo.saveAll(productInventoryItems);
    }

}

package com.modmed.training.ecommerce.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(final String message) {
       super(message);
    }
}

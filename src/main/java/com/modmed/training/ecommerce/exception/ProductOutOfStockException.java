package com.modmed.training.ecommerce.exception;

public class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException(final String message) {
        super(message);
    }
}

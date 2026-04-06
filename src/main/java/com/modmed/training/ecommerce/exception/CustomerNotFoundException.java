package com.modmed.training.ecommerce.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(final String message){
        super(message);
    }
}

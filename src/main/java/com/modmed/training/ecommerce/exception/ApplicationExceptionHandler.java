package com.modmed.training.ecommerce.exception;

import com.modmed.training.ecommerce.exception.dto.RuntimeExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler({ProductOutOfStockException.class})
    public ResponseEntity<RuntimeExceptionDto> productOutOfStockEx(ProductOutOfStockException ex){
        return new ResponseEntity<>(RuntimeExceptionDto.builder().statusCode(HttpStatus.BAD_REQUEST.toString()).errorMessage(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<RuntimeExceptionDto> productOutOfStockEx(ProductNotFoundException ex){
        return new ResponseEntity<>(RuntimeExceptionDto.builder().statusCode(HttpStatus.BAD_REQUEST.toString()).errorMessage(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    public  ResponseEntity<RuntimeExceptionDto> customerNotFoundEx(CustomerNotFoundException ex){
        return new ResponseEntity<>(RuntimeExceptionDto.builder().statusCode(HttpStatus.BAD_REQUEST.toString()).errorMessage(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<RuntimeExceptionDto> getException(RuntimeException ex) {
        return new ResponseEntity<>(RuntimeExceptionDto.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString()).errorMessage(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

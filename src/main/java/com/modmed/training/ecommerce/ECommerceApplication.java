package com.modmed.training.ecommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "E-Commerce API",
                version = "1.0",
                description = "API for managing products, customers, and orders in an e-commerce application",
                contact = @Contact(
                        name = "Yandakuditi Sandeep",
                        email = "sandeep@email.com",
                        url = "https://www.linkedin.com/in/sandeep-yandakuditi-9a1b65209/"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)
@SpringBootApplication
public class ECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }

}

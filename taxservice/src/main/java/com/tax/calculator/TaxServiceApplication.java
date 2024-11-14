package com.tax.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Tax Service API", version = "1.0", description = "APIs for tax calculation"))
public class TaxServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxServiceApplication.class, args);
	}

}

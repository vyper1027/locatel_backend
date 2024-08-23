package com.ventas.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ventas API", version = "1.0", description = "Ventas API"))
public class PruebaVentasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaVentasApiApplication.class, args);
	}

}

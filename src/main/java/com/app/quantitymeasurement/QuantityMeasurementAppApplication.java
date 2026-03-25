package com.app.quantitymeasurement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Quantity Measurements API",
                version = "v1",
                description = "REST API for quantity measurement operations"
        ),
        servers = @Server(url = "http://localhost:8080", description = "Generated server url")
)
public class QuantityMeasurementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementAppApplication.class, args);
    }
}

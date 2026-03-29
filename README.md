# Quantity Measurement App - UC17

## Overview

This project implements **UC17: Spring Framework Integration - REST Services and JPA for Quantity Measurement**.
It migrates the application to Spring Boot while preserving UC1-UC16 business behavior.

## Key Capabilities

- Spring Boot application with embedded Tomcat.
- REST endpoints under `/api/v1/quantities`.
- Persistence through Spring Data JPA.
- Validation and centralized exception handling with `@ControllerAdvice`.
- OpenAPI/Swagger documentation.
- Spring Actuator health and metrics endpoints.
- Basic Spring Security configuration (allow-all, ready for future hardening).

## API and Docs Endpoints

- REST base: `/api/v1/quantities`
- Swagger UI: `/swagger-ui.html`
- OpenAPI docs: `/api-docs`
- H2 Console (dev): `/h2-console`
- Actuator health: `/actuator/health`
- Actuator metrics: `/actuator/metrics`

## Profiles

- `application.properties`: development defaults (H2 in-memory DB, debug logs).
- `application-prod.properties`: production-oriented MySQL settings.

## Build and Test

```bash
mvn clean test
```

```bash
mvn spring-boot:run
```

Test coverage includes controller tests (`@WebMvcTest` + MockMvc), integration tests (`@SpringBootTest` + TestRestTemplate), and repository tests (`@DataJpaTest`).

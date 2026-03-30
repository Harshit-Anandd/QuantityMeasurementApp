# Quantity Measurement App — UC17: Spring Framework Integration

## Overview

This module implements **Use Case 17 — Spring Framework Integration** for the Quantity Measurement App.

The application is transformed from a standalone, JDBC-based system into a **Spring Boot REST API**, while preserving all business logic developed in previous use cases.

This upgrade introduces enterprise-grade features such as dependency injection, RESTful endpoints, ORM-based persistence, validation, and API documentation.

---

## Architecture

The application follows a layered Spring architecture:

### Controller Layer
Exposes REST endpoints for quantity operations.

### Service Layer
Implements business logic for comparison, conversion, and arithmetic operations.

### Repository Layer
Uses Spring Data JPA for database interaction and persistence.

### Model Layer
Defines entities, units, and measurement categories.

### DTO Layer
Handles API input/output using validated data transfer objects.

### Configuration Layer
Manages application properties and security settings.

---

## Key Features

### Spring Boot Integration
- Auto-configuration
- Embedded server
- Simplified project setup

### REST API
- Endpoints for compare, convert, add, subtract, and history
- JSON-based request/response handling

### Spring Data JPA
- Replaces JDBC with ORM
- Provides automatic CRUD operations
- Supports custom query methods

### Validation
- Input validation using annotations
- Ensures correctness of API requests

### Exception Handling
- Centralized error handling
- Structured API responses

### API Documentation
- Swagger/OpenAPI integration for interactive documentation

### Security (Basic Setup)
- Spring Security configuration
- Stateless and CORS-enabled setup

### Monitoring
- Spring Boot Actuator for health checks and metrics

---

## API Capabilities

Supports operations across:

- Length
- Weight
- Volume
- Temperature

Operations include:

- Comparison
- Conversion
- Addition / Subtraction
- Multiplication / Division
- Operation history retrieval
- Operation count tracking

---

## Database

- Development: H2 in-memory database
- Production: Configurable (MySQL/PostgreSQL)

JPA handles object-relational mapping and persistence.

---

## Testing

The system uses Spring Boot testing tools to validate:

- REST endpoints
- Service logic
- Repository operations
- End-to-end workflows

---

## Conclusion

UC17 upgrades the application into a **modern Spring Boot REST service**, improving:

- Maintainability
- Scalability
- Developer productivity
- Integration readiness

It establishes a strong foundation for future enhancements such as authentication, microservices, and cloud deployment.

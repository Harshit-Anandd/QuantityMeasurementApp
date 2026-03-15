# Quantity Measurement App — UC15: N-Tier Architecture

## Overview

This module implements **Use Case 15 — N-Tier Architecture Refactoring** for the Quantity Measurement App.

Earlier use cases introduced measurement functionality such as equality checks, unit conversions, arithmetic operations, and support for multiple measurement categories. UC15 reorganizes the codebase to follow a structured **N-Tier architecture**, separating responsibilities across multiple layers.

The result is a modular and maintainable system that cleanly separates presentation logic, business logic, domain models, and utility services.

---

## Architecture

The application now follows a layered architecture consisting of several tiers:

### Controller Layer
Handles incoming requests and coordinates operations.  
Example: `QuantityMeasurementController`

### Service Layer
Contains core business logic for measurement operations such as conversion, comparison, and arithmetic.

### Domain / Model Layer
Defines measurement entities and unit definitions:

- Length units
- Weight units
- Volume units
- Temperature units
- Measurement category abstractions

### Utility Layer
Encapsulates centralized arithmetic operations used across all measurement categories.

### DTO Layer
Data Transfer Objects allow safe communication between layers without exposing internal models.

---

## Benefits

Adopting N-Tier architecture provides several advantages:

- **Separation of Concerns**  
  Each layer focuses on a specific responsibility.

- **Maintainability**  
  Changes in one layer do not affect others.

- **Scalability**  
  Business logic and presentation logic can evolve independently.

- **Testability**  
  Each layer can be tested in isolation.

---

## Measurement Capabilities

The system supports multiple measurement categories:

- Length
- Weight
- Volume
- Temperature

Each category supports:

- Equality comparison
- Unit conversion
- Arithmetic operations (where meaningful)

Temperature operations enforce domain-specific restrictions (e.g., disallowing unsupported arithmetic).

---

## Testing

The UC15 test suite validates:

- Controller and service interactions
- Measurement conversions and equality logic
- Arithmetic operations across categories
- Category safety enforcement
- Exception handling for invalid operations

Tests are structured to verify behavior independently across layers.

---

## Conclusion

UC15 completes the architectural evolution of the Quantity Measurement App by introducing a clean **N-Tier structure**.  

This refactoring improves maintainability, modularity, and extensibility while preserving the full measurement functionality developed in previous use cases.

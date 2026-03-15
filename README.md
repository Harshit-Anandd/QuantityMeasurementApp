# Quantity Measurement App — UC16: Database Integration

## Overview

This module implements **Use Case 16 — Database Integration** for the Quantity Measurement App.

Earlier use cases supported measurement operations such as unit conversion, arithmetic, and comparison entirely in memory. UC16 extends the system by introducing a **database persistence layer**, allowing measurement records to be stored, retrieved, and managed across application sessions.

This enhancement builds upon the N-Tier architecture introduced in UC15 and adds a repository layer responsible for database communication.

---

## Architecture

The application now follows a layered architecture consisting of:

### Controller Layer
Handles incoming requests and delegates operations to the service layer.

### Service Layer
Implements business logic including measurement conversion, comparison, and arithmetic operations.

### Repository Layer
Responsible for database communication and persistence.

Typical operations include:

- Creating measurement records
- Retrieving stored measurements
- Updating existing entries
- Deleting records

### Domain / Model Layer
Defines measurement entities and unit types:

- Length
- Weight
- Volume
- Temperature

### Utility Layer
Contains centralized arithmetic logic shared across measurement categories.

### Configuration Layer
Manages database connection setup and configuration.

---

## Database Integration

The repository layer interacts with a relational database to perform CRUD operations. This enables:

- Persistent storage of measurement data
- Retrieval of historical measurements
- Structured data management
- Reliable integration with business logic

---

## Supported Measurement Categories

The system supports the following measurement types:

- Length
- Weight
- Volume
- Temperature

Each category supports unit conversion, equality checks, and arithmetic operations where applicable.

---

## Testing

The UC16 test suite verifies:

- Successful database persistence of measurement records
- Correct retrieval and update of stored measurements
- Integrity of measurement data across database operations
- Proper interaction between controller, service, and repository layers
- Arithmetic correctness when operating on persisted data

---

## Conclusion

UC16 extends the Quantity Measurement App by integrating a persistent database layer. This improvement:

- Enables long-term storage of measurement records
- Enhances scalability for real-world usage
- Maintains clean separation of concerns through the N-Tier architecture
- Preserves all measurement capabilities developed in earlier use cases

# Quantity Measurement App — UC10: Generic Category Unit

## Overview

This module implements **Use Case 10 – Generic Category Unit**, which extends the measurement framework to support a **generic unit abstraction** capable of handling units and values across multiple measurement domains (e.g., length, weight).  

The design introduces a generic measurement abstraction that treats units such as feet, inches, kilograms, grams, etc., as instances of a common unit category hierarchy, enabling reuse of core behaviors like comparison, conversion, and addition without duplication.

---

## Implementation

### Generic Unit Abstraction

The core of UC10 is a generic interface or base type (e.g., `UnitCategory`) representing any measurable unit. This allows:

- Unit conversion logic to be encapsulated in each unit type (length or weight)
- A unified API for operations that work across unit categories
- A generic measurement class (`GenericMeasurement<T>`) parameterized by the unit type

### GenericMeasurement

A single class now encapsulates:

- A numeric value
- A unit of generic type `T`, which must implement the measurement category interface

Shared behaviors such as:

- Equality checks
- Conversion between units within a category
- Addition with optional target unit

are implemented generically without category-specific code duplication.

This pattern supports both **length measurements** (feet, inches, yards) and **weight measurements** (kilograms, grams, pounds) seamlessly.

---

## Behavior

### Cross-Category Generic Operations

Operations such as:

- `equals()` for same category units
- `convertTo()` for converting values within a category
- `add()` for summing measurements

work identically regardless of the category being length or weight, thanks to the generic abstraction.

### Safeguards

- Generic type ensures that comparisons and arithmetic only occur within the same category type.
- Null and invalid inputs are handled safely by contract.

---

## Testing

The test suite for UC10 verifies:

- Generic equality across units within the same category
- Conversion between units within the same category
- Addition and addition with target unit for both length and weight categories
- Safety against invalid cross-category comparisons

Tests use descriptive names and follow behavior-driven naming conventions to clearly express expected outcomes.

---

## Conclusion

UC10 successfully generalizes the measurement framework by:

- Creating a reusable generic measurement abstraction
- Eliminating category-specific code duplication
- Supporting multiple unit categories with a single API
- Preserving correctness and safety guarantees

This design prepares the codebase for future expansion into additional measurable domains (volume, temperature, etc.) with minimal duplication and maximum reuse.

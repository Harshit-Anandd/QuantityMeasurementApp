# Quantity Measurement App — UC12: Arithmetic Operations

## Overview

This module implements **Use Case 12 — Arithmetic Operations** for the Quantity Measurement App.  
It extends the generic measurement framework to support core arithmetic operations — **addition, subtraction, multiplication, and division** — across any supported measurement category (length, weight, volume, etc.).

The operations are category-aware, type-safe, and follow a consistent normalization and conversion strategy, enabling accurate cross-unit calculations and optional target-unit results.

---

## Implementation

### Generic Arithmetic Pattern

The core class (`GenericMeasurement<T>`, where `T` is a unit category) now supports:

- **add(other, targetUnit)**
- **subtract(other, targetUnit)**
- **multiply(factor, targetUnit)**
- **divide(divisor, targetUnit)**

Each operation follows the same pattern:

1. **Normalize operands** to the internal base unit via the unit abstraction.
2. Perform the arithmetic on normalized values.
3. **Convert the result** to the specified target unit (if provided).
4. Return a new measurement instance in the target unit.

### Safety and Constraints

- Arithmetic only permitted within the same category (no length vs weight).
- `null` operands result in safe exceptions.
- Division by zero is guarded and throws a meaningful exception.
- Arithmetic involving mixed units (like feet + meters) is handled via normalization.

---

## Behavior

### Addition
- Sum of two measurements, optionally expressed in a target unit.
- Handles same-unit and cross-unit combinations.

### Subtraction
- Difference between two values, normalized first.
- Result shown in a target unit if specified.

### Multiplication
- Multiply a measurement by a scalar (numeric factor).
- Optional output target unit conversion.

### Division
- Divide one measurement by another or by a scalar.
- Includes safeguards for division by zero.

---

## Testing

The test suite for UC12 validates:

- Basic arithmetic operations in various unit combinations
- Target unit specification across all categories
- Category safety (only same category operations succeed)
- Null safety and invalid inputs
- Edge cases like division by zero
- Combined operations (e.g., (a + b) * scale)

Tests are organized with descriptive names and confirm behavior across length, weight, and volume categories.

---

## Conclusion

UC12 successfully implements full arithmetic capabilities for generic measurements by:

- Normalizing values before operation
- Delegating conversion logic to the unit abstraction
- Converting results to target units
- Preserving safety contracts and category integrity

This enables expressive and accurate measurement arithmetic across all supported domains.

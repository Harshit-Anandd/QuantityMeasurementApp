# Quantity Measurement App — UC13: Centralized Arithmetic Logic

## Overview

This module implements **Use Case 13 – Centralized Arithmetic Logic** in the Quantity Measurement App.  
Previous use cases introduced arithmetic operations across measurement categories (length, weight, volume).  
UC13 refactors this behavior by centralizing all arithmetic logic into a single core component — removing duplication and improving maintainability.

The central processor now handles addition, subtraction, multiplication, and division for all measurement categories while preserving safety, conversion accuracy, and category integrity.

---

## Architecture

### Central Arithmetic Processor

A dedicated processor encapsulates arithmetic behavior across all measurement domains. It:

- Normalizes measurement values using unit conversion logic
- Validates category consistency (e.g., length with length)
- Executes arithmetic operations
- Converts results to the specified target unit

This allows all measurement categories (length, weight, volume, etc.) to benefit from a single source of arithmetic truth.

### GenericMeasurement Delegation

The generic measurement type no longer implements arithmetic directly.  
Instead, it delegates to the central processor, thereby separating concerns:

- Measurement entity → Data and delegation
- Central processor → Core arithmetic logic

This reduces duplication and improves extensibility.

---

## Supported Operations

All operations support:

- Same unit inputs
- Cross-unit inputs (via normalization)
- Target unit specification for results

### Arithmetic Methods

- `add(...)`
- `subtract(...)`
- `multiply(...)`
- `divide(...)`

Each method:
1. Normalizes values to a base unit
2. Performs arithmetic in the base domain
3. Converts the result into the requested target unit
4. Produces a new measurement object

---

## Category Integrity and Safety

Operations are constrained by:

- **Category safety:** Only measurements of the same category (length, weight, volume, etc.) can be combined.
- **Null safety:** Null operands are rejected safely.
- **Division safety:** Division by zero is handled with exceptions.

---

## Testing

The UC13 test suite validates:

- Arithmetic consistency across categories
- Proper handling of normalization and conversion
- Safe error conditions (null, wrong categories, invalid arithmetic)
- Target unit result correctness  
Tests are organized with descriptive names reflecting expected behaviors.

---

## Conclusion

Use Case 13 strengthens the measurement framework by centralizing arithmetic logic. This enhancement:

- Improves maintainability
- Eliminates duplication
- Ensures uniform behavior across all measurement domains
- Supports extension to future categories

The central arithmetic processor is now the canonical implementation of measurement arithmetic in the project.

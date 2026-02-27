# Quantity Measurement App — UC3: Generic Length Equality

## Overview

This module implements **UC3 (Generic Length Equality)** of the Quantity Measurement App.  
The goal is to represent length measurements such as feet and inches under a **single abstract type** while preserving type safety, null safety, and correct equality behavior.

Rather than having separate classes for each length unit (as in UC1 and UC2), UC3 introduces a generalized structure allowing future extensions to additional length units without repeating comparison logic.

---

## Implementation

### GenericLength Abstraction

The class `GenericLength` encapsulates:

- A `double` value representing the measurement
- A `LengthUnit` enum indicating the unit type (e.g., FEET or INCHES)

Equality is determined using a normalized comparison technique:

1. Convert both measurements to a common base representation
2. Use `Double.compare()` for floating-point accuracy
3. Respect null and type safety

This design ensures that same-unit measurements are compared correctly with minimal duplication.

---

## Code Behavior

### Components

#### `LengthUnit` enum

Defines supported length units:
- `FEET`
- `INCHES`

This enum can be extended to add more length units in the future.

#### `GenericLength`

Implements:
- A type-safe description of a length value
- Normalization logic for value comparison
- Overridden `equals()` and `hashCode()` methods

Equality comparisons ensure:
- Same reference ⇒ `true`
- Null or incompatible type ⇒ `false`
- Same unit and same numeric value ⇒ `true`

Cross-unit equality (e.g., feet vs inches) is **not** part of UC3 and will be handled in future use cases.

---

## Testing

Comprehensive tests validate core behaviors:

- Equality of same unit with identical values
- Inequality of same unit with different values
- Null and incompatible type safety
- Reflexivity of object equality

Test names are descriptive and follow a given-when-then style to clearly document expected behavior.

Example tests:

- `testEquality_SameFeetValueReturnsTrue`
- `testEquality_DifferentFeetValueReturnsFalse`
- `testEquality_SameInchValueReturnsTrue`
- `testEquality_NullComparisonReturnsFalse`

---

## Conclusion

UC3 successfully generalizes length comparisons by introducing a reusable abstraction for lengths.  
This implementation:

- Reduces duplication
- Provides a clear extension path for additional units
- Ensures consistent and safe equality semantics

Future use cases will expand this framework to include cross-unit conversions and additional measurement types.

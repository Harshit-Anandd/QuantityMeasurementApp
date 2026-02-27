# Quantity Measurement App — UC4: Yard Equality

## Overview

This module implements the **UC4 use case — Yard Equality** for the Quantity Measurement App.  
It extends the generic length abstraction to support **yard measurements** while preserving type safety, null safety, and consistent equality semantics.

UC4 ensures that yard values are compared correctly, following an established contract for object equality and numeric comparison.

---

## Implementation

### GenericLength Abstraction

This use case builds on the `GenericLength` abstraction introduced in UC3.  
Instead of having separate classes for individual units, a single class is used with a `LengthUnit` enum specifying the unit type.

For UC4, the supported length units include:
- `FEET`
- `INCHES`
- `YARD`

The `GenericLength` class encapsulates:
- A double measurement value
- A `LengthUnit` to indicate which unit the value represents

Equality between two `GenericLength` objects of the same unit is evaluated through a normalization and comparison strategy that ensures accuracy, null safety, and type safety.

---

## Application Behavior

Comparisons for yard measurements follow a well-defined process:
1. Same object references return `true`
2. Objects of incompatible types or `null` return `false`
3. Two yard measurements with identical numerical values return `true`
4. Different numerical values return `false`

Cross-unit comparisons (e.g., comparing yards to feet or inches) are not covered in UC4.

---

## Testing

Unit tests validate the following scenarios:

- **Same yard value equals true:**  
  `new GenericLength(5.0, LengthUnit.YARD).equals(...)`

- **Different yard values:**  
  Unequal results if numeric values differ

- **Null comparison:**  
  Equals should return `false` when comparing with `null`

- **Different type comparison:**  
  Equals should return `false` when comparing with a different class

- **Reference equality:**  
  Same object returns `true`

Unit test names are descriptive and follow a given-when-then style to clearly document expected behavior.

---

## Conclusion

UC4 successfully extends the length measurement domain with yard support while maintaining consistent correctness and safety.  
It demonstrates how the system can grow to include additional unit types without violating core design principles or comparison contracts.

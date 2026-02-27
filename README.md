# Quantity Measurement App — UC2: Feet and Inches Equality

## Overview

This repository implements **UC2**, an extension of the Quantity Measurement App focused on **equality checks** for both **Feet** and **Inches** measurements.  
The core objective is to verify that two measurements in the same unit produce accurate and predictable equality results while preserving null and type safety.

---

## Implementation

### Unit Classes

#### `Feet`
- Encapsulates a measurement value in **feet**.
- Overrides `equals(Object)` to:
  - Ensure reflexive equality
  - Return `false` for `null` and incompatible types
  - Use `Double.compare()` for numeric equivalence

#### `Inches`
- Mirrors the `Feet` implementation for measurements in **inches**
- Applies the same safety and comparison semantics

Both classes also override `hashCode()`.

---

## Application Behavior

The `main` method in `QuantityMeasurementApp`:
1. Prompts the user to enter two feet values and prints whether they are equal.
2. Prompts the user for two inches values and prints whether they are equal.
3. Handles invalid numeric input via exception catching.

The application does **not compare feet to inches** — equality is only within the same unit type.

---

## Testing

Unit tests exercise both Feet and Inches equality contracts:

- Identical values are considered equal.
- Different values are not equal.
- Comparing to `null` returns `false`.
- Comparing against different types returns `false`.
- Same object references are equal.

These tests confirm the expected behavior described in UC2.

---

## Conclusion

This implementation captures the UC2 specification with minimal and robust Java code.  
It demonstrates safe equality practices, clear unit separation, and thorough validation via unit tests.

Future enhancements may focus on eliminating duplication via abstraction once multiple quantity types are introduced.

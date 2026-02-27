# Quantity Measurement App — UC7: Target Unit Addition

## Overview

This module implements **UC7: Target Unit Addition**, an extension of the length addition functionality in the Quantity Measurement App.

UC7 allows two length measurements — whether in the same unit or different units — to be **added together and expressed in any specified target unit** of measurement (for example, feet, inches, or yards). This is achieved by normalizing operands to a base representation, summing them, and converting the result to the desired unit.

---

## Implementation

### GenericLength & LengthUnit

The `GenericLength` class encapsulates a measurement as a value and a `LengthUnit` (FEET, INCHES, YARD).

The addition method for UC7 accepts:
- Two `GenericLength` operands
- A `LengthUnit` target unit

Steps:
1. Convert both operands to a base representation (normalized)
2. Add their normalized values
3. Convert the sum into the specified *target unit*
4. Return a new `GenericLength` with the result value in the target unit

Example target units:
- FEET  
- INCHES  
- YARD

Conversion factors are maintained in the `LengthUnit` enum.

---

## Behavior

### Add & Convert to Target Unit

- **Feet Result:**  
  1 foot + 12 inches → 2 feet

- **Inches Result:**  
  2 feet + 1 foot → 36 inches

- **Yard Result:**  
  6 feet + 3 feet → 3 yards

Each result respects conversion factors and returns a new `GenericLength` object.

---

## Testing

The UC7 test suite validates:
- Correct sum calculation across mixed unit inputs
- Correct conversion to the target unit
- Null safety when incorrect or missing parameters are provided
- Type safety when operands or target types are invalid

Tests are named descriptively for easy documentation and verification.

---

## Conclusion

UC7 successfully extends addition logic by incorporating target unit conversion.  
It demonstrates:
- Accurate cross-unit arithmetic
- Clean conversion abstractions
- Preservation of equality and safety semantics
- A flexible API for returning results in any supported length unit

This implementation forms a foundation for arithmetic expressions combining mixed units and target output control.

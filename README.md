# Quantity Measurement App — UC5: Unit Conversion

## Overview

This module implements the **Unit Conversion** use case (UC5) in the Quantity Measurement App.  
UC5 expands the length abstraction to support **conversion and comparison between different units** — such as feet, inches, and yards — by normalizing values to a common base representation.

The goal is to allow units from different measurement systems to be compared meaningfully while preserving type safety and equality correctness.

---

## Implementation

### GenericLength and LengthUnit

The core abstraction remains the `GenericLength` class, which encapsulates:

- A numeric `value`
- A `LengthUnit` identifying the type of measurement

The `LengthUnit` enum defines conversion constants that allow normalization of all units back to a **base unit (feet)**:

- **FEET** — base unit
- **INCHES** — expressed as fractional feet
- **YARD** — expressed as multiple feet

---

## Conversion Logic

To support cross-unit comparison, the application:

1. Normalizes values to a common base (feet)
2. Uses `Double.compare()` to compare normalized values
3. Maintains null and type safety via overridden `equals()` logic

This ensures correct behavior such as:

- 1 foot equals 12 inches
- 1 yard equals 3 feet
- 3 feet equals 36 inches

---

## Testing

Unit tests validate both equality and conversion behavior:

- **Cross-unit equality**
  - Feet vs inches
  - Feet vs yards
  - Inches vs yards
- **Inequality**
  - Values that should not equate after conversion
- **Safety**
  - Null comparison returns false
  - Incompatible types return false
  - Reference equality returns true

Each test case is named descriptively and follows a clear given-when-then convention.

---

## Conclusion

UC5 successfully implements unit conversion for length measurements by:

- Normalizing all units to a base representation
- Supporting meaningful cross-unit comparison
- Preserving safety and correctness
- Maintaining an extensible abstraction for future unit types

This provides a solid basis for further use cases involving arithmetic operations or additional unit systems.

---

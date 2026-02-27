# Quantity Measurement App — UC11: Volume Measurement

## Overview

This module implements **UC11: Volume Measurement** as part of the Quantity Measurement App.  
UC11 extends the generic measurement framework to support a new measurement category — **volume** — allowing correctness-guaranteed operations such as:

- **Equality comparison** between volume values
- **Unit conversion** within the volume category
- **Addition** of volume values with optional target unit specification

This design follows the same pattern introduced in UC10 for generic measurement categories, ensuring clean abstraction, strong type safety, and consistent behavior.

---

## Supported Volume Units

The volume category includes units such as:

- **LITER** — base unit
- **MILLILITER** — smaller unit (1 L = 1000 mL)
- **GALLON** — larger unit (1 gal ≈ 3.78541 L)

Each unit encapsulates its conversion relationship relative to the base (liter), enabling reliable inter-unit operations.

---

## Implementation

### VolumeUnit

A standalone enum/class representing volume units with conversion logic:

- Provides methods to normalize values to the base unit (liter)
- Converts from the base unit back into the specified target unit

This abstraction encapsulates volume-specific conversion behavior, keeping it separate from measurement logic.

---

## Generic Volume Measurement

A generic measurement class (e.g., `GenericMeasurement<VolumeUnit>`) is used to represent volume values with:

- A numeric measurement
- An associated `VolumeUnit`

It supports:

- **Equality comparison:** Normalizes both operands to a base unit before comparing
- **Conversion:** Converts values from one unit to another
- **Addition:** Sums normalized values and optionally expresses the result in a target unit

All operations preserve null safety and are strictly within the volume category.

---

## Testing

The UC11 test suite validates:

### Equality

- Same unit comparisons (e.g., 1 L equals 1 L)
- Cross-unit comparisons using conversion (e.g., 1000 mL equals 1 L)

### Conversion

- Converting between volume units accurately (e.g., L ↔ mL, L ↔ gallon)

### Addition

- Addition of same and different volume units
- Specifying a target unit for the result

### Safety and Category Enforcement

- Null and type safety
- Category mismatch rejection (e.g., length vs volume)

Descriptive test names help document expected behaviors.

---

## Conclusion

UC11 completes the volume measurement extension of the Quantity Measurement App.  
It demonstrates:

- Clean generic abstraction across measurement categories
- Consistent behavior for equality, conversion, and addition
- Reuse of established patterns (from UC10) for extensibility
- Strong safety and type correctness guarantees

This use case equips the system for further extension into additional measurement domains as needed.

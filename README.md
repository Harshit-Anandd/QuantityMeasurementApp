# Quantity Measurement App — UC14: Temperature Measurement

## Overview

This module implements **Use Case 14 — Temperature Measurement**, extending the Quantity Measurement App to support temperature as a new measurement category.  

Unlike length, weight, and volume — which support full arithmetic — temperature only supports **comparisons** and **unit conversions**. Arithmetic operations such as addition, multiplication, and division do *not* generally make sense for absolute temperatures and are therefore disallowed.

This use case also **refactors the generic measurement interface** (`IMeasurable`) to allow selective operational support per category.

---

## Supported Temperature Units

The temperature category includes:

- **Celsius (°C)**
- **Fahrenheit (°F)**
- **Kelvin (K)**

Conversions between these units follow standard scientific formulas and handle offsets as appropriate:
- \(°F = (°C × 9/5) + 32\)
- \(°C = (°F − 32) × 5/9\)
- \(K = °C + 273.15\)

---

## Behavior

### Equality

Temperature values are compared by normalizing through conversion logic:
- 0°C equals 32°F
- -40°C equals -40°F
- 0°C equals 273.15K
- Equality is reflexive, symmetric, and transitive when conversion is correct.

### Conversion

Conversions between any pair of temperature units use accurate formulas that account for scale and offset.

### Arithmetic Operations

Because arithmetic on *absolute* temperatures is not generally meaningful:

- **Unsupported:** `add()`, `divide()`, `multiply()`  
  → Throws `UnsupportedOperationException` with a clear message  
- **Partially Supported:** `subtract()` may represent a *difference* between two temperature measurements  
  → Throws exception or is explicitly guarded

### Category Safety

- Temperature measurements cannot be compared with length, weight, or volume.  
- Comparing across categories returns `false` or throws a safe exception.

---

## Interface Refactoring

The `IMeasurable` interface has been updated to include **default methods** indicating supported operations. Categories can override these defaults to communicate which operations are meaningful for them.

This enables:
- Legacy categories (length, weight, volume) to continue full arithmetic support
- Temperature category to override unsupported operations cleanly
- Future categories to enforce their own constraints

---

## Testing

The UC14 test suite validates:

1. **Temperature Equality**
   - Same and cross-unit equality (with precise conversion)
2. **Temperature Conversion**
   - Correct bidirectional conversions for all unit pairs
3. **Unsupported Operation Handling**
   - Calls to `add()`, `multiply()`, `divide()` throw `UnsupportedOperationException`
   - Meaningful exception messages
4. **Safety**
   - Null unit handling
   - Cross-category type safety (temperature vs other categories)

Tests use epsilon tolerance for floating-point precision where appropriate.

---

## Conclusion

UC14 successfully:

- Integrates the **temperature measurement** category
- Enables accurate **equality** and **conversion**
- Disallows inappropriate arithmetic
- Refactors the interface to support selective operation capabilities per category
- Maintains cross-category safety and backward compatibility with previous use cases

This design supports scalable extension to future measurement categories with specific operational needs.

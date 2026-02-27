# Quantity Measurement App — UC9: Weight Measurement

## Overview

This update extends the Quantity Measurement App to support **weight measurements** as a separate category from length.  
It introduces a dedicated `WeightUnit` enum and a corresponding quantity class (`QuantityWeight`) to enable:

- **Equality checks** across kilogram, gram, and pound
- **Unit conversions** between all supported weight units
- **Addition operations** with optional target output unit

This implementation follows the same scalable design patterns used for length measurements (UC1–UC8), reinforcing consistency, maintainability, and type safety across measurement categories.

---

## Supported Units

Weight units supported in this use case:

- **Kilogram (kg)** — base unit
- **Gram (g)** — 1 kg = 1000 g
- **Pound (lb)** — 1 lb ≈ 0.453592 kg

`WeightUnit` encapsulates conversion behavior relative to the base unit.

---

## Implementation

### WeightUnit

A standalone enum that encapsulates:

- Conversion factors relative to the base unit (kg)
- Methods:
  - `convertToBaseUnit(double)`
  - `convertFromBaseUnit(double)`

This places **conversion responsibility within the unit**, following the same pattern established for length units.

### QuantityWeight

Encapsulates:

- A numeric weight value
- A `WeightUnit` indicating its unit
- Immutable instances for safe reuse

Implements:

- `equals()` for equality via base-unit normalization
- `convertTo(WeightUnit target)` for unit conversion
- `add()` for:
  - Implicit target (first operand’s unit)
  - Explicitly specified output unit

All operations return new instances, preserving immutability.

---

## Behavior

### Equality

- Comparing weights in the same unit returns correct results.
- Cross-unit equality uses normalization via `WeightUnit`.
- Comparing a weight with a length returns `false` or is rejected.

### Conversion

- Converting between any pair of weight units yields mathematically correct results within floating-point tolerance.
- Round-trip conversions preserve values.

### Addition

- Same unit addition returns straightforward sums.
- Cross-unit addition involves normalization, summation, and optional conversion to a target unit.
- Addition is commutative and supports zero and negative values.

---

## Testing

The test suite verifies:

1. **Within-unit equality**
2. **Cross-unit equality**
3. **Unit conversions (all combinations)**
4. **Addition with default and explicit target units**
5. **Edge cases** — null comparisons, negative and large values
6. **Category safety** — weight and length are incompatible

Tests are descriptive and validate the expected behavior of all weight operations.

---

## Conclusion

UC9 successfully expands the app’s measurement capabilities to include the weight category with full support for:

- Equality
- Conversion
- Addition

This use case demonstrates the extensibility of the generic measurement design pattern and maintains strong architectural consistency across multiple measurement domains.

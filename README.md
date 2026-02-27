# Quantity Measurement App — UC1: Feet Equality

## Overview

This project demonstrates the first use case of the Quantity Measurement App: **comparing two measurements expressed in feet for equality**.

The goal is to encapsulate a measurement in a type with correct equality semantics, enforce null/type safety, and provide predictable comparison results under typical use and edge cases.

---

## Implementation Details

### Feet Representation

The `QuantityMeasurementApp.Feet` class models a measurement in feet using a `double` value.  
Equality between two feet measurements is determined using the overridden `equals(Object)` method, which:

- Returns `true` when both objects are the same reference
- Safely handles `null` and incompatible types
- Uses `Double.compare()` for numeric equality

This confirms correct behavior in line with the UC1 specification.

---

## Usage

The application can be run from the command line via the `main` method, which:

1. Prompts the user to enter two feet values
2. Parses them into double values
3. Compares them
4. Prints whether they are equal

If non-numeric values are entered, the program outputs an error message.

---

## Testing

Unit tests in `QuantityMeasurementAppTest` validate the core behaviors:

- `testEquality_SameValue`: Equal numeric values return `true`
- `testEquality_DifferentValue`: Different values return `false`
- `testEquality_NullComparison`: Comparison with `null` returns `false`
- `testEquality_SameReference`: Same object returns `true`
- `testEquality_NonNumericInputHandled`: Comparison with incompatible type returns `false`

These tests confirm both functional correctness and null/type safety.

---

## Conclusion

This UC1 implementation establishes a clean and minimal foundation for measurement equality in the Quantity Measurement App.  
It demonstrates:

- Proper object equality semantics
- Defensive programming (null/type safety)
- Accurate numeric comparison
- Clear unit tests validating intended behavior

Further quantity types and operations will extend this foundation in subsequent use cases.

# Quantity Measurement App — UC6: Unit Addition

## Overview

This module implements the **Unit Addition (UC6)** use case of the Quantity Measurement App.  
UC6 extends the generic length framework to support **addition of two measurements**, potentially expressed in different length units such as feet, inches, or yards, by normalizing values to a common base and returning a combined result.

---

## Implementation

### GenericLength Abstraction

The central class `GenericLength` encapsulates a numeric value and an associated `LengthUnit` (e.g., FEET, INCHES, YARD).

To support addition:
1. Both operands are normalized to a base unit (**feet**).
2. Their normalized values are summed.
3. A new `GenericLength` instance is returned containing the **combined value expressed in feet**.

This approach preserves correctness while supporting cross-unit addition (e.g., feet + inches).

---

## Supported Scenarios

### **Feet + Feet**
Values are directly summed after normalization.

### **Inches + Inches**
Each inch value is first converted to feet before addition.

### **Feet + Inches (or vice versa)**
Both units are normalized to feet, added, and represented in feet.

### **Yard + Feet / Yard + Inches**
Yards, being larger units, are normalized to feet prior to addition.

The resulting combined value is always represented in the base unit (feet).

---

## Testing

The test suite verifies:

- **Homogeneous additions** (e.g., feet + feet, inches + inches)
- **Heterogeneous additions** (feet + inches, inches + feet)
- **Combined additions involving yards**
- **Correct conversion behavior before addition**
- **Descriptive test names specifying expected outcomes**

Sample tests include:

- `testAdd_TwoFeet_ReturnsThreeFeet`
- `testAdd_OneFootAndTwelveInches_ReturnsTwoFeet`
- `testAdd_OneYardAndTwoFeet_ReturnsFiveFeet`

Each test confirms both numeric correctness and consistent use of unit conversion.

---

## Conclusion

UC6 successfully implements length addition with support for unit conversion by:

- Normalizing all inputs to a common base unit
- Summing normalized values
- Returning accurate combined results
- Preserving type and null safety

This use case establishes a foundation for future arithmetic enhancements and cross-domain expansions.

# Quantity Measurement App

## 1. Project Overview

The **Quantity Measurement App** is a type-safe unit comparison and conversion system designed to model real-world physical quantities such as Length, Weight, and Temperature.

The system allows quantities expressed in different units to be compared, converted, and manipulated arithmetically while preserving domain correctness and mathematical consistency.

Rather than being a simple unit converter, the project focuses on building a scalable and extensible measurement framework using disciplined software engineering practices.

---

## 2. Problem Statement

Handling measurements across multiple unit systems introduces three core challenges:

* Ensuring accurate cross-unit comparison
* Preventing invalid operations across incompatible domains
* Maintaining extensibility without introducing code duplication

This project addresses these challenges through base-unit normalization, generic abstraction, and structured testing.

---

## 3. System Design

### Base Unit Normalization

Each unit category defines a canonical base unit.
All quantities are internally converted to this base unit before comparison or arithmetic operations.

This guarantees numerical consistency and eliminates unit-specific branching logic.

### Type-Safe Generic Architecture

The system uses Java Generics to enforce domain-level correctness at compile time.

For example:

* Length quantities can only interact with Length units
* Weight quantities cannot be added to Temperature

This ensures strong domain modeling and prevents logical errors before runtime.

### Incremental Extensibility

New quantity domains can be added without modifying existing logic, adhering to the Open–Closed Principle.

---

## 4. Development Methodology

This project follows a **Hybrid Engineering Model** combining:

### Test-Driven Development (TDD)

At the component level:

* Tests are written before implementation
* Minimal code is introduced to satisfy failing tests
* Refactoring is performed safely under test coverage

This approach ensures correctness, regression safety, and cleaner API evolution.

### Design–Develop–Test (DDT)

At the project level:

* Requirements are defined clearly
* High-level architecture is designed upfront
* Features are implemented incrementally
* Integration and validation testing conclude each phase

The hybrid model balances architectural planning with iterative correctness.

---

## 5. Testing Strategy

The system emphasizes high-confidence validation through:

* Unit-level equality tests
* Cross-unit conversion validation
* Arithmetic operation verification
* Edge case handling (zero values, negative values, precision tolerance)

Tests function not only as validation artifacts but also as behavioral documentation.

---

## 6. Key Engineering Outcomes

This project demonstrates:

* Clean domain modeling
* Type-safe generic design
* Incremental development discipline
* Refactor-safe architecture
* Enterprise-level testing structure

It serves as a strong foundation for building scalable measurement systems or extending into API-based or UI-driven applications.

---

# UC1: Feet Equality

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

---

# UC2: Feet and Inches Equality

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

---

# UC3: Generic Length Equality

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

---

# UC4: Yard Equality

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

---

# UC5: Unit Conversion

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

# UC6: Unit Addition

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

---

# UC7: Target Unit Addition

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

---

# UC8: Standalone Unit Refactoring

## Overview

This module implements **Use Case 8 (UC8)**: *Refactoring Unit Enum to Standalone with Conversion Responsibility*.

The key design improvement in this use case is to decouple conversion logic from the quantity implementation by moving it into a standalone `LengthUnit` enum. This promotes separation of concerns, reduces coupling, and adheres to the Single Responsibility Principle.

The refactored design supports all existing behaviors from previous use cases (UC1–UC7) and paves the way for future measurement categories (e.g., weight, volume).

---

## Implementation

### Standalone `LengthUnit`

The `LengthUnit` enum is now a top-level construct that encapsulates:

- Conversion factors relative to a base unit (feet).
- Methods:
  - `convertToBaseUnit(double value)` – Normalize a value to the base unit.
  - `convertFromBaseUnit(double baseValue)` – Convert a base unit value back to this unit.

### Simplified Quantity Logic

The quantity class (`GenericLength` or the main application class) now:

- Delegates all conversions to `LengthUnit`.
- Focuses on arithmetic and comparison logic.
- No longer contains conversion formulas.

This separation eliminates circular dependencies and allows future extensibility.

---

## Design Benefits

- **Separation of concerns:**  
  Unit conversion is handled exclusively by `LengthUnit`.  
  Quantity logic focuses on behavior like equality and addition.

- **Backward compatibility:**  
  All existing use cases and tests (UC1–UC7) continue working without modification.

- **Architectural scalability:**  
  The pattern supports adding future categories (WeightUnit, VolumeUnit) with minimal coupling.

---

## Testing

The UC8 test suite verifies:

- Correctness of conversion methods in `LengthUnit`.
- Delegated operations in quantity methods (equality, conversion, addition).
- Backward compatibility with earlier use cases.
- Robustness against invalid inputs (null units, non-numeric values).

Descriptive test names help document expected behaviors clearly.

---

## Conclusion

UC8 successfully:

- Refactors unit logic into a dedicated enum.
- Improves architectural clarity and maintainability.
- Preserves all existing functionality.
- Establishes a scalable template for future measurement categories.

This improvement strengthens the core design and readies the codebase for sustainable growth.

---

# UC9: Weight Measurement

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

---

# UC10: Generic Category Unit

## Overview

This module implements **Use Case 10 – Generic Category Unit**, which extends the measurement framework to support a **generic unit abstraction** capable of handling units and values across multiple measurement domains (e.g., length, weight).  

The design introduces a generic measurement abstraction that treats units such as feet, inches, kilograms, grams, etc., as instances of a common unit category hierarchy, enabling reuse of core behaviors like comparison, conversion, and addition without duplication.

---

## Implementation

### Generic Unit Abstraction

The core of UC10 is a generic interface or base type (e.g., `UnitCategory`) representing any measurable unit. This allows:

- Unit conversion logic to be encapsulated in each unit type (length or weight)
- A unified API for operations that work across unit categories
- A generic measurement class (`GenericMeasurement<T>`) parameterized by the unit type

### GenericMeasurement

A single class now encapsulates:

- A numeric value
- A unit of generic type `T`, which must implement the measurement category interface

Shared behaviors such as:

- Equality checks
- Conversion between units within a category
- Addition with optional target unit

are implemented generically without category-specific code duplication.

This pattern supports both **length measurements** (feet, inches, yards) and **weight measurements** (kilograms, grams, pounds) seamlessly.

---

## Behavior

### Cross-Category Generic Operations

Operations such as:

- `equals()` for same category units
- `convertTo()` for converting values within a category
- `add()` for summing measurements

work identically regardless of the category being length or weight, thanks to the generic abstraction.

### Safeguards

- Generic type ensures that comparisons and arithmetic only occur within the same category type.
- Null and invalid inputs are handled safely by contract.

---

## Testing

The test suite for UC10 verifies:

- Generic equality across units within the same category
- Conversion between units within the same category
- Addition and addition with target unit for both length and weight categories
- Safety against invalid cross-category comparisons

Tests use descriptive names and follow behavior-driven naming conventions to clearly express expected outcomes.

---

## Conclusion

UC10 successfully generalizes the measurement framework by:

- Creating a reusable generic measurement abstraction
- Eliminating category-specific code duplication
- Supporting multiple unit categories with a single API
- Preserving correctness and safety guarantees

This design prepares the codebase for future expansion into additional measurable domains (volume, temperature, etc.) with minimal duplication and maximum reuse.

---

# UC11: Volume Measurement

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

---

# UC12: Arithmetic Operations

## Overview

This module implements **Use Case 12 — Arithmetic Operations** for the Quantity Measurement App.  
It extends the generic measurement framework to support core arithmetic operations — **addition, subtraction, multiplication, and division** — across any supported measurement category (length, weight, volume, etc.).

The operations are category-aware, type-safe, and follow a consistent normalization and conversion strategy, enabling accurate cross-unit calculations and optional target-unit results.

---

## Implementation

### Generic Arithmetic Pattern

The core class (`GenericMeasurement<T>`, where `T` is a unit category) now supports:

- **add(other, targetUnit)**
- **subtract(other, targetUnit)**
- **multiply(factor, targetUnit)**
- **divide(divisor, targetUnit)**

Each operation follows the same pattern:

1. **Normalize operands** to the internal base unit via the unit abstraction.
2. Perform the arithmetic on normalized values.
3. **Convert the result** to the specified target unit (if provided).
4. Return a new measurement instance in the target unit.

### Safety and Constraints

- Arithmetic only permitted within the same category (no length vs weight).
- `null` operands result in safe exceptions.
- Division by zero is guarded and throws a meaningful exception.
- Arithmetic involving mixed units (like feet + meters) is handled via normalization.

---

## Behavior

### Addition
- Sum of two measurements, optionally expressed in a target unit.
- Handles same-unit and cross-unit combinations.

### Subtraction
- Difference between two values, normalized first.
- Result shown in a target unit if specified.

### Multiplication
- Multiply a measurement by a scalar (numeric factor).
- Optional output target unit conversion.

### Division
- Divide one measurement by another or by a scalar.
- Includes safeguards for division by zero.

---

## Testing

The test suite for UC12 validates:

- Basic arithmetic operations in various unit combinations
- Target unit specification across all categories
- Category safety (only same category operations succeed)
- Null safety and invalid inputs
- Edge cases like division by zero
- Combined operations (e.g., (a + b) * scale)

Tests are organized with descriptive names and confirm behavior across length, weight, and volume categories.

---

## Conclusion

UC12 successfully implements full arithmetic capabilities for generic measurements by:

- Normalizing values before operation
- Delegating conversion logic to the unit abstraction
- Converting results to target units
- Preserving safety contracts and category integrity

This enables expressive and accurate measurement arithmetic across all supported domains.

---

# UC13: Centralized Arithmetic Logic

## Overview

This module implements **Use Case 13 – Centralized Arithmetic Logic** in the Quantity Measurement App.  
Previous use cases introduced arithmetic operations across measurement categories (length, weight, volume).  
UC13 refactors this behavior by centralizing all arithmetic logic into a single core component — removing duplication and improving maintainability.

The central processor now handles addition, subtraction, multiplication, and division for all measurement categories while preserving safety, conversion accuracy, and category integrity.

---

## Architecture

### Central Arithmetic Processor

A dedicated processor encapsulates arithmetic behavior across all measurement domains. It:

- Normalizes measurement values using unit conversion logic
- Validates category consistency (e.g., length with length)
- Executes arithmetic operations
- Converts results to the specified target unit

This allows all measurement categories (length, weight, volume, etc.) to benefit from a single source of arithmetic truth.

### GenericMeasurement Delegation

The generic measurement type no longer implements arithmetic directly.  
Instead, it delegates to the central processor, thereby separating concerns:

- Measurement entity → Data and delegation
- Central processor → Core arithmetic logic

This reduces duplication and improves extensibility.

---

## Supported Operations

All operations support:

- Same unit inputs
- Cross-unit inputs (via normalization)
- Target unit specification for results

### Arithmetic Methods

- `add(...)`
- `subtract(...)`
- `multiply(...)`
- `divide(...)`

Each method:
1. Normalizes values to a base unit
2. Performs arithmetic in the base domain
3. Converts the result into the requested target unit
4. Produces a new measurement object

---

## Category Integrity and Safety

Operations are constrained by:

- **Category safety:** Only measurements of the same category (length, weight, volume, etc.) can be combined.
- **Null safety:** Null operands are rejected safely.
- **Division safety:** Division by zero is handled with exceptions.

---

## Testing

The UC13 test suite validates:

- Arithmetic consistency across categories
- Proper handling of normalization and conversion
- Safe error conditions (null, wrong categories, invalid arithmetic)
- Target unit result correctness  
Tests are organized with descriptive names reflecting expected behaviors.

---

## Conclusion

Use Case 13 strengthens the measurement framework by centralizing arithmetic logic. This enhancement:

- Improves maintainability
- Eliminates duplication
- Ensures uniform behavior across all measurement domains
- Supports extension to future categories

The central arithmetic processor is now the canonical implementation of measurement arithmetic in the project.

---

# UC14: Temperature Measurement

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

---

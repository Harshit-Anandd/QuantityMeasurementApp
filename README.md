# Quantity Measurement App

A type-safe unit comparison and conversion system built using Java.

## Overview

The **Quantity Measurement App** provides a structured way to:

* Compare quantities across different units
* Convert between compatible units
* Perform arithmetic operations within the same quantity domain
* Extend support to additional measurement types

The system emphasizes correctness, maintainability, and scalable design.

---

## Features

* Unit comparison (equality across different units)
* Base unit normalization strategy
* Arithmetic operations (add, subtract)
* Support for multiple quantity domains (Length, Weight, Temperature, etc.)
* Generic, extensible architecture
* Comprehensive unit testing

---

## Design Approach

### Base Unit Normalization

All units are internally converted to a base unit before comparison or arithmetic operations.
This ensures consistent results and prevents floating-point inconsistencies.

### Type Safety via Generics

Java Generics are used to prevent invalid cross-domain operations at compile time (e.g., Length cannot be added to Weight).

---

## Development Methodology

This project follows a **Hybrid Model**:

### Test-Driven Development (TDD)

* Write failing tests first
* Implement minimal logic
* Refactor safely
* Maintain high test coverage

### Design–Develop–Test (DDT)

* Requirement analysis
* High-level design
* Incremental feature development
* Integration and validation

This combination ensures architectural clarity and micro-level correctness.

---

## Future Enhancements

* Additional quantity domains
* REST API integration
* UI layer
* Persistence support

---

## Purpose

This project demonstrates:

* Clean architecture
* Strong domain modeling
* Incremental development
* Enterprise-level testing discipline

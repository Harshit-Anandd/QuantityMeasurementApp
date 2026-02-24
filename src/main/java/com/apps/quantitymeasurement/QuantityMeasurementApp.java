/**
 * QuantityMeasurementApp - UC9: Add Weight Measurement Support
 *
 * Extends UC8 by introducing WeightUnit and QuantityWeight
 * while maintaining strict separation between categories.
 */
package com.apps.quantitymeasurement;

import java.util.Objects;

public class QuantityMeasurementApp {

    private static final double EPSILON = 0.0001;

    // ===================== LENGTH =====================

    public enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(1.0 / 30.48);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double convertToBaseUnit(double value) {
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Value must be finite.");
            return value * toFeetFactor;
        }

        public double convertFromBaseUnit(double baseValue) {
            if (!Double.isFinite(baseValue))
                throw new IllegalArgumentException("Value must be finite.");
            return baseValue / toFeetFactor;
        }
    }

    public static final class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Value must be finite.");

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null.");

            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.convertToBaseUnit(value);
        }

        public double getValue() { return value; }

        public LengthUnit getUnit() { return unit; }

        public static QuantityLength add(
                QuantityLength first,
                QuantityLength second,
                LengthUnit targetUnit) {

            if (first == null || second == null)
                throw new IllegalArgumentException("Operands cannot be null.");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null.");

            double baseSum = first.toBase() + second.toBase();
            double resultValue = targetUnit.convertFromBaseUnit(baseSum);

            return new QuantityLength(resultValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;
            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toBase() - other.toBase()) < EPSILON;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Math.round(toBase() / EPSILON));
        }
    }

    // ===================== WEIGHT =====================

    public enum WeightUnit {

        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double toKgFactor;

        WeightUnit(double toKgFactor) {
            this.toKgFactor = toKgFactor;
        }

        public double convertToBaseUnit(double value) {
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Value must be finite.");
            return value * toKgFactor;
        }

        public double convertFromBaseUnit(double baseValue) {
            if (!Double.isFinite(baseValue))
                throw new IllegalArgumentException("Value must be finite.");
            return baseValue / toKgFactor;
        }
    }

    public static final class QuantityWeight {

        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {

            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Value must be finite.");

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null.");

            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.convertToBaseUnit(value);
        }

        public double getValue() { return value; }

        public WeightUnit getUnit() { return unit; }

        public static QuantityWeight add(
                QuantityWeight first,
                QuantityWeight second) {
            return add(first, second, first.unit);
        }

        public static QuantityWeight add(
                QuantityWeight first,
                QuantityWeight second,
                WeightUnit targetUnit) {

            if (first == null || second == null)
                throw new IllegalArgumentException("Operands cannot be null.");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null.");

            double baseSum = first.toBase() + second.toBase();
            double resultValue = targetUnit.convertFromBaseUnit(baseSum);

            return new QuantityWeight(resultValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityWeight)) return false;
            QuantityWeight other = (QuantityWeight) obj;
            return Math.abs(this.toBase() - other.toBase()) < EPSILON;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Math.round(toBase() / EPSILON));
        }
    }
}
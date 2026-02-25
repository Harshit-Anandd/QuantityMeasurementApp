/**
 * QuantityMeasurementApp - UC13: Centralized Arithmetic Logic (DRY Enforcement)
 *
 * Refactors arithmetic logic (add, subtract, divide)
 * by centralizing validation and base conversion logic
 * into a single reusable method.
 */
package com.apps.quantitymeasurement;

import java.util.Objects;

public class QuantityMeasurementApp {

	private static final double EPSILON = 0.0001;

	// =========================================================
	// Interface
	// =========================================================

	public interface IMeasurable {
		double convertToBaseUnit(double value);
		double convertFromBaseUnit(double baseValue);
		double getConversionFactor();
		String getUnitName();
	}

	// =========================================================
	// Length Units (Base: FEET)
	// =========================================================

	public enum LengthUnit implements IMeasurable {
		FEET(1.0),
		INCH(1.0 / 12.0),
		YARD(3.0),
		CENTIMETER(1.0 / 30.48);

		private final double factor;

		LengthUnit(double factor) {
			this.factor = factor;
		}

		public double convertToBaseUnit(double value) {
			validate(value);
			return value * factor;
		}

		public double convertFromBaseUnit(double baseValue) {
			validate(baseValue);
			return baseValue / factor;
		}

		public double getConversionFactor() { return factor; }
		public String getUnitName() { return name(); }
	}

	// =========================================================
	// Weight Units (Base: KILOGRAM)
	// =========================================================

	public enum WeightUnit implements IMeasurable {
		KILOGRAM(1.0),
		GRAM(0.001),
		POUND(0.453592);

		private final double factor;

		WeightUnit(double factor) {
			this.factor = factor;
		}

		public double convertToBaseUnit(double value) {
			validate(value);
			return value * factor;
		}

		public double convertFromBaseUnit(double baseValue) {
			validate(baseValue);
			return baseValue / factor;
		}

		public double getConversionFactor() { return factor; }
		public String getUnitName() { return name(); }
	}

	// =========================================================
	// Volume Units (Base: LITRE)
	// =========================================================

	public enum VolumeUnit implements IMeasurable {
		LITRE(1.0),
		MILLILITRE(0.001),
		GALLON(3.78541);

		private final double factor;

		VolumeUnit(double factor) {
			this.factor = factor;
		}

		public double convertToBaseUnit(double value) {
			validate(value);
			return value * factor;
		}

		public double convertFromBaseUnit(double baseValue) {
			validate(baseValue);
			return baseValue / factor;
		}

		public double getConversionFactor() { return factor; }
		public String getUnitName() { return name(); }
	}

	private static void validate(double value) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");
	}

	// =========================================================
	// Generic Quantity (UC13 Refactored)
	// =========================================================

	public static final class Quantity<U extends IMeasurable> {

		private final double value;
		private final U unit;

		public Quantity(double value, U unit) {
			validate(value);
			if (unit == null)
				throw new IllegalArgumentException("Unit cannot be null.");
			this.value = value;
			this.unit = unit;
		}

		public double getValue() { return value; }
		public U getUnit() { return unit; }

		private double toBase() {
			return unit.convertToBaseUnit(value);
		}

		private double roundToTwoDecimals(double val) {
			return Math.round(val * 100.0) / 100.0;
		}

		// =========================================================
		// UC13 CENTRALIZED ARITHMETIC CORE
		// =========================================================

		private enum ArithmeticOperation {
			ADD {
				double apply(double a, double b) { return a + b; }
			},
			SUBTRACT {
				double apply(double a, double b) { return a - b; }
			},
			DIVIDE {
				double apply(double a, double b) { return a / b; }
			};

			abstract double apply(double a, double b);
		}

		private double performBaseArithmetic(
				Quantity<?> other,
				ArithmeticOperation operation) {

			if (other == null)
				throw new IllegalArgumentException("Operand cannot be null.");

			if (!this.unit.getClass().equals(other.unit.getClass()))
				throw new IllegalArgumentException(
						"Cannot operate on different quantity categories.");

			double base1 = this.toBase();
			double base2 = other.unit.convertToBaseUnit(other.value);

			if (operation == ArithmeticOperation.DIVIDE &&
					Math.abs(base2) < EPSILON)
				throw new ArithmeticException("Division by zero.");

			return operation.apply(base1, base2);
		}

		// =========================================================
		// ADD
		// =========================================================

		public Quantity<U> add(Quantity<U> other) {
			return add(other, this.unit);
		}

		public Quantity<U> add(Quantity<U> other, U targetUnit) {

			if (targetUnit == null)
				throw new IllegalArgumentException("Target unit cannot be null.");

			double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
			double converted = targetUnit.convertFromBaseUnit(baseResult);

			return new Quantity<>(converted, targetUnit);
		}

		// =========================================================
		// SUBTRACT
		// =========================================================

		public Quantity<U> subtract(Quantity<U> other) {
			return subtract(other, this.unit);
		}

		public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

			if (targetUnit == null)
				throw new IllegalArgumentException("Target unit cannot be null.");

			double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
			double converted = targetUnit.convertFromBaseUnit(baseResult);

			return new Quantity<>(
					roundToTwoDecimals(converted),
					targetUnit
					);
		}

		// =========================================================
		// DIVIDE
		// =========================================================

		public double divide(Quantity<?> other) {
			return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
		}

		// =========================================================
		// EQUALS / HASHCODE / TOSTRING
		// =========================================================

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (!(obj instanceof Quantity<?>)) return false;

			Quantity<?> other = (Quantity<?>) obj;

			if (!unit.getClass().equals(other.unit.getClass()))
				return false;

			return Math.abs(this.toBase() - other.toBase()) < EPSILON;
		}

		@Override
		public int hashCode() {
			return Objects.hash(Math.round(toBase() / EPSILON), unit.getClass());
		}

		@Override
		public String toString() {
			return value + " " + unit.getUnitName();
		}
	}
}
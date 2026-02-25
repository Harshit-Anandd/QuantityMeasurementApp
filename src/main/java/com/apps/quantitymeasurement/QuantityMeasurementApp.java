/**
 * QuantityMeasurementApp - UC14:
 * Temperature Measurement with Selective Arithmetic Support
 *
 * Adds TemperatureUnit with restricted arithmetic.
 * Refactors IMeasurable to allow optional arithmetic support.
 */
package com.apps.quantitymeasurement;

import java.util.Objects;

public class QuantityMeasurementApp {

	private static final double EPSILON = 0.0001;

	// =========================================================
	// Interface (UC14 Refactored)
	// =========================================================

	public interface IMeasurable {
		double convertToBaseUnit(double value);
		double convertFromBaseUnit(double baseValue);
		double getConversionFactor();
		String getUnitName();

		// UC14: Selective arithmetic support
		default boolean supportsArithmetic() {
			return true;
		}
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

	// =========================================================
	// Temperature Units (Base: KELVIN) - UC14
	// =========================================================

	public enum TemperatureUnit implements IMeasurable {
		KELVIN,
		CELSIUS,
		FAHRENHEIT;

		public double convertToBaseUnit(double value) {
			validate(value);

			switch (this) {
			case KELVIN:
				return value;
			case CELSIUS:
				return value + 273.15;
			case FAHRENHEIT:
				return (value - 32) * 5 / 9 + 273.15;
			default:
				throw new IllegalStateException("Unexpected unit.");
			}
		}

		public double convertFromBaseUnit(double baseValue) {
			validate(baseValue);

			switch (this) {
			case KELVIN:
				return baseValue;
			case CELSIUS:
				return baseValue - 273.15;
			case FAHRENHEIT:
				return (baseValue - 273.15) * 9 / 5 + 32;
			default:
				throw new IllegalStateException("Unexpected unit.");
			}
		}

		// Not meaningful for temperature (offset-based conversion)
		public double getConversionFactor() {
			throw new UnsupportedOperationException(
					"Temperature uses offset conversion, not factor-based conversion.");
		}

		public String getUnitName() { return name(); }

		// UC14: Arithmetic not supported
		@Override
		public boolean supportsArithmetic() {
			return false;
		}
	}

	private static void validate(double value) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");
	}

	// =========================================================
	// Generic Quantity (UC14 Updated)
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

		private enum ArithmeticOperation {
			ADD { double apply(double a, double b) { return a + b; }},
			SUBTRACT { double apply(double a, double b) { return a - b; }},
			DIVIDE { double apply(double a, double b) { return a / b; }};

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

			// UC14: Restrict arithmetic for temperature
			if (!this.unit.supportsArithmetic())
				throw new UnsupportedOperationException(
						"Arithmetic operations are not supported for temperature quantities.");

			double base1 = this.toBase();
			double base2 = other.unit.convertToBaseUnit(other.value);

			if (operation == ArithmeticOperation.DIVIDE &&
					Math.abs(base2) < EPSILON)
				throw new ArithmeticException("Division by zero.");

			return operation.apply(base1, base2);
		}

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

		public double divide(Quantity<?> other) {
			return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
		}

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
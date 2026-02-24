/**
 * QuantityMeasurementApp - UC10: Generic Quantity Refactor
 *
 * Refactors Length and Weight implementations into
 * a single generic Quantity<U extends IMeasurable>.
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
	// Length Units
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

		@Override
		public double convertToBaseUnit(double value) {
			if (!Double.isFinite(value))
				throw new IllegalArgumentException("Value must be finite.");
			return value * factor;
		}

		@Override
		public double convertFromBaseUnit(double baseValue) {
			if (!Double.isFinite(baseValue))
				throw new IllegalArgumentException("Value must be finite.");
			return baseValue / factor;
		}

		@Override
		public double getConversionFactor() {
			return factor;
		}

		@Override
		public String getUnitName() {
			return name();
		}
	}

	// =========================================================
	// Weight Units
	// =========================================================

	public enum WeightUnit implements IMeasurable {

		KILOGRAM(1.0),
		GRAM(0.001),
		POUND(0.453592);

		private final double factor;

		WeightUnit(double factor) {
			this.factor = factor;
		}

		@Override
		public double convertToBaseUnit(double value) {
			if (!Double.isFinite(value))
				throw new IllegalArgumentException("Value must be finite.");
			return value * factor;
		}

		@Override
		public double convertFromBaseUnit(double baseValue) {
			if (!Double.isFinite(baseValue))
				throw new IllegalArgumentException("Value must be finite.");
			return baseValue / factor;
		}

		@Override
		public double getConversionFactor() {
			return factor;
		}

		@Override
		public String getUnitName() {
			return name();
		}
	}
	
	// =========================================================
	// Volume Units (UC11)
	// Base Unit: LITRE
	// =========================================================

	public enum VolumeUnit implements IMeasurable {

	    LITRE(1.0),
	    MILLILITRE(0.001),
	    GALLON(3.78541);   // US Gallon

	    private final double factor;

	    VolumeUnit(double factor) {
	        this.factor = factor;
	    }

	    @Override
	    public double convertToBaseUnit(double value) {
	        if (!Double.isFinite(value))
	            throw new IllegalArgumentException("Value must be finite.");
	        return value * factor;
	    }

	    @Override
	    public double convertFromBaseUnit(double baseValue) {
	        if (!Double.isFinite(baseValue))
	            throw new IllegalArgumentException("Value must be finite.");
	        return baseValue / factor;
	    }

	    @Override
	    public double getConversionFactor() {
	        return factor;
	    }

	    @Override
	    public String getUnitName() {
	        return name();
	    }
	}

	// =========================================================
	// Generic Quantity
	// =========================================================

	public static final class Quantity<U extends IMeasurable> {

		private final double value;
		private final U unit;

		public Quantity(double value, U unit) {

			if (!Double.isFinite(value))
				throw new IllegalArgumentException("Value must be finite.");

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

		public Quantity<U> convertTo(U targetUnit) {

			if (targetUnit == null)
				throw new IllegalArgumentException("Target unit cannot be null.");

			double base = toBase();
			double converted = targetUnit.convertFromBaseUnit(base);

			return new Quantity<>(converted, targetUnit);
		}

		public Quantity<U> add(Quantity<U> other) {

			if (other == null)
				throw new IllegalArgumentException("Operand cannot be null.");

			return add(other, this.unit);
		}

		public Quantity<U> add(Quantity<U> other, U targetUnit) {

			if (other == null)
				throw new IllegalArgumentException("Operand cannot be null.");

			if (targetUnit == null)
				throw new IllegalArgumentException("Target unit cannot be null.");

			double baseSum = this.toBase() + other.toBase();
			double converted = targetUnit.convertFromBaseUnit(baseSum);

			return new Quantity<>(converted, targetUnit);
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj) return true;
			if (!(obj instanceof Quantity)) return false;

			Quantity<?> other = (Quantity<?>) obj;

			if (!this.unit.getClass().equals(other.unit.getClass()))
				return false;

			return Math.abs(this.toBase() - other.toBase()) < EPSILON;
		}

		@Override
		public int hashCode() {
			return Objects.hash(Math.round(toBase() / EPSILON),
					unit.getClass());
		}

		@Override
		public String toString() {
			return value + " " + unit.getUnitName();
		}
	}
}
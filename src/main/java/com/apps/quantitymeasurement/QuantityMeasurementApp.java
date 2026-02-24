/**
 * QuantityMeasurementApp - UC7: Refactor QuantityLength
 *
 * Refactor the QuantityLength class to delegate unit conversion
 * responsibilities to a standalone LengthUnit enum. 
 *
 */
package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.Scanner;

public class QuantityMeasurementApp {

	private static final double EPSILON = 0.0001;

	// ENUM: LengthUnit: Stores conversion factor relative to base unit (inches).
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

		public double getValue() {
			return value;
		}

		public LengthUnit getUnit() {
			return unit;
		}

		private double toBase() {
			return unit.convertToBaseUnit(value);
		}

		public static QuantityLength add(
				QuantityLength first,
				QuantityLength second,
				LengthUnit targetUnit) {

			if (first == null || second == null)
				throw new IllegalArgumentException("Operands cannot be null.");

			if (targetUnit == null)
				throw new IllegalArgumentException("Target unit cannot be null.");

			double baseSum =
					first.toBase() + second.toBase();

			double resultValue =
					targetUnit.convertFromBaseUnit(baseSum);

			return new QuantityLength(resultValue, targetUnit);
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj) return true;
			if (!(obj instanceof QuantityLength)) return false;

			QuantityLength other = (QuantityLength) obj;

			return Math.abs(
					this.toBase() - other.toBase()
					) < EPSILON;
		}

		@Override
		public int hashCode() {
			return Objects.hash(
					Math.round(toBase() / EPSILON)
					);
		}
	}
}
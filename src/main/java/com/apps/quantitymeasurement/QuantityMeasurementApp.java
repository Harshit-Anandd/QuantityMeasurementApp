/**
 * QuantityMeasurementApp - UC7: Addition with Explicit Target Unit
 *
 * Extends UC6 by allowing the caller to explicitly specify the target unit
 * in which the addition result should be returned.
 *
 */
package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.Scanner;

public class QuantityMeasurementApp {

	private static final double EPSILON = 0.0001;

	// ENUM: LengthUnit: Stores conversion factor relative to base unit (inches).
	public enum LengthUnit {

		FEET(12),
		INCH(1),
		YARD(36),
		CENTIMETER(0.393701);

		private final double toBaseFactor;

		LengthUnit(double toBaseFactor) {
			this.toBaseFactor = toBaseFactor;
		}

		public double toBase(double value) {
			return value * toBaseFactor;
		}

		public double fromBase(double baseValue) {
			return baseValue / toBaseFactor;
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

		private double toBaseUnit() {
			return unit.toBase(value);
		}

		public double getValue() {
			return value;
		}

		public LengthUnit getUnit() {
			return unit;
		}

		// UC7 Core Method
		public static QuantityLength add(
				QuantityLength first,
				QuantityLength second,
				LengthUnit targetUnit) {

			if (first == null || second == null)
				throw new IllegalArgumentException("Operands cannot be null.");

			if (targetUnit == null)
				throw new IllegalArgumentException("Target unit cannot be null.");

			double sumBase =
					first.toBaseUnit() + second.toBaseUnit();

			double resultValue =
					targetUnit.fromBase(sumBase);

			return new QuantityLength(resultValue, targetUnit);
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;

			QuantityLength other = (QuantityLength) obj;

			return Math.abs(
					this.toBaseUnit() - other.toBaseUnit()
					) < EPSILON;
		}

		@Override
		public int hashCode() {
			return Objects.hash(
					Math.round(toBaseUnit() / EPSILON)
					);
		}
	}
}
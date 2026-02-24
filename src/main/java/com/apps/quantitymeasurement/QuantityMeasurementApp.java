/**
 * QuantityMeasurementApp - UC4: Extended Unit Support
 *
 * Extend UC3 generic measurement implementation to support additional units
 * without duplicating code (DRY principle).
 *
 */
package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.Scanner;

public class QuantityMeasurementApp {

	// ENUM: LengthUnit: Stores conversion factor relative to base unit (inches).
	private static final double EPSILON = 0.0001;

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

	/*
	 * Static conversion method as preferred in UC5 documentation.
	 */
	public static double convert(
			double value,
			LengthUnit source,
			LengthUnit target) {

		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");

		if (source == null || target == null)
			throw new IllegalArgumentException("Units cannot be null.");

		double baseValue = source.toBase(value);
		return target.fromBase(baseValue);
	}

	/*
	 * Equality-based Quantity class retained from UC4
	 */
	public static final class QuantityLength {

		private final double value;
		private final LengthUnit unit;

		public QuantityLength(double value, LengthUnit unit) {
			if (unit == null)
				throw new IllegalArgumentException("Unit cannot be null.");

			this.value = value;
			this.unit = unit;
		}

		private double toBaseUnit() {
			return unit.toBase(value);
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;

			if (obj == null || getClass() != obj.getClass())
				return false;

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

	private static LengthUnit parseUnit(String input) {

		if (input.equalsIgnoreCase("feet"))
			return LengthUnit.FEET;

		if (input.equalsIgnoreCase("inch") ||
				input.equalsIgnoreCase("inches"))
			return LengthUnit.INCH;

		if (input.equalsIgnoreCase("yard") ||
				input.equalsIgnoreCase("yards"))
			return LengthUnit.YARD;

		if (input.equalsIgnoreCase("cm") ||
				input.equalsIgnoreCase("centimeter") ||
				input.equalsIgnoreCase("centimeters"))
			return LengthUnit.CENTIMETER;

		throw new IllegalArgumentException("Invalid unit type.");
	}

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {

			System.out.println("Enter value to convert:");
			double value = Double.parseDouble(sc.nextLine());

			System.out.println("Enter source unit (feet/inches/yards/cm):");
			LengthUnit source = parseUnit(sc.nextLine());

			System.out.println("Enter target unit (feet/inches/yards/cm):");
			LengthUnit target = parseUnit(sc.nextLine());

			double result = convert(value, source, target);

			System.out.println("Converted Value: " + result);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
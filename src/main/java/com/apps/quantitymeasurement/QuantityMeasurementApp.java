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
	public enum LengthUnit {

		FEET(12),
		INCH(1),
		YARD(36),
		CENTIMETER(0.393701);

		private final double toBaseFactor;

		LengthUnit(double toBaseFactor) {
			this.toBaseFactor = toBaseFactor;
		}

		// Converts given value into base unit (inches).
		public double toBase(double value) {
			return value * toBaseFactor;
		}
	}

	public static final class QuantityLength {

		private static final double EPSILON = 0.0001;
		private final double value;
		private final LengthUnit unit;

		/*
		 * Constructor validates that unit is not null.
		 */
		public QuantityLength(double value, LengthUnit unit) {

			if (unit == null) {
				throw new IllegalArgumentException("Unit cannot be null.");
			}

			this.value = value;
			this.unit = unit;
		}

		/*
		 * Converts current measurement to base unit (inches).
		 */
		private double toBaseUnit() {
			return unit.toBase(value);
		}

		/*
		 * Overrides equals() method to compare two QuantityLength objects.
		 *
		 * Compares two QuantityLength objects by:
		 *  - Converting both values to base unit (inches)
		 *  - Performing tolerance-based comparison using EPSILON
		 *
		 * Prevents floating-point precision issues during unit conversion.
		 */
		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;

			if (obj == null || getClass() != obj.getClass())
				return false;

			QuantityLength other = (QuantityLength) obj;

			double difference = Math.abs(
					this.toBaseUnit() - other.toBaseUnit()
					);

			return difference < EPSILON;
		}

		/*
		 * hashCode overridden to maintain consistency with equals().
		 */
		@Override
		public int hashCode() {
			return Objects.hash(Math.round(toBaseUnit() / EPSILON));
		}

		@Override
		public String toString() {
			return value + " " + unit;
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

		throw new IllegalArgumentException("Invalid unit type entered.");
	}

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {

			// First Measurement
			System.out.println("Enter first value:");
			double value1 = Double.parseDouble(sc.nextLine());

			System.out.println("Enter first unit (feet/inches/yards/cm):");
			LengthUnit unit1 = parseUnit(sc.nextLine());

			// Second Measurement
			System.out.println("Enter second value:");
			double value2 = Double.parseDouble(sc.nextLine());

			System.out.println("Enter second unit (feet/inches/yards/cm):");
			LengthUnit unit2 = parseUnit(sc.nextLine());

			QuantityLength measurement1 =
					new QuantityLength(value1, unit1);

			QuantityLength measurement2 =
					new QuantityLength(value2, unit2);

			boolean result = measurement1.equals(measurement2);

			System.out.println("Are the two measurements equal? " + result);

		} catch (NumberFormatException e) {
			System.err.println("Invalid numeric value entered.");
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
}
/**
 * QuantityMeasurementApp - UC3: Generic Quantity Measurement
 *
 * Refactor UC1 and UC2 implementations into a single generic class to
 * eliminate duplication (DRY principle) and support cross-unit equality.
 * 
 */
package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.Scanner;

public class QuantityMeasurementApp {

	// ENUM: LengthUnit: Represents supported measurement units and their conversion factors relative to the base unit (inches).
	public enum LengthUnit {

		FEET(12),   // 1 foot = 12 inches
		INCH(1);    // Base unit

		private final double toBaseFactor;

		LengthUnit(double toBaseFactor) {
			this.toBaseFactor = toBaseFactor;
		}

		/*
		 * Converts given value into base unit (inches).
		 */
		public double toBase(double value) {
			return value * toBaseFactor;
		}
	}

	public static final class QuantityLength {

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
		 *  Convert both measurements to base unit (inches)
		 *  Compare using Double.compare()
		 */
		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;

			if (obj == null || getClass() != obj.getClass())
				return false;

			QuantityLength other = (QuantityLength) obj;

			return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
		}

		/*
		 * hashCode overridden to maintain consistency with equals().
		 */
		@Override
		public int hashCode() {
			return Objects.hash(toBaseUnit());
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

		throw new IllegalArgumentException("Invalid unit type entered.");
	}

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {

			// ----- First Measurement -----
			System.out.println("Enter first value:");
			double value1 = Double.parseDouble(sc.nextLine());

			System.out.println("Enter first unit (feet/inches):");
			LengthUnit unit1 = parseUnit(sc.nextLine());

			// ----- Second Measurement -----
			System.out.println("Enter second value:");
			double value2 = Double.parseDouble(sc.nextLine());

			System.out.println("Enter second unit (feet/inches):");
			LengthUnit unit2 = parseUnit(sc.nextLine());

			// Create objects
			QuantityLength measurement1 =
					new QuantityLength(value1, unit1);

			QuantityLength measurement2 =
					new QuantityLength(value2, unit2);

			// Perform equality comparison
			boolean result = measurement1.equals(measurement2);

			// Display result
			System.out.println("Are the two measurements equal? " + result);

		} catch (NumberFormatException e) {
			System.err.println("Invalid numeric value entered.");
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
}
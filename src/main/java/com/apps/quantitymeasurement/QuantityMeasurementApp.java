/**
 * QuantityMeasurementApp - UC2: Inch measurement equality
 *
 * This class is responsible for checking the equality of two numerical values
 * measured in feet and inches in the Quantity Measurement Application.
 *
 */


package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.Scanner;

public class QuantityMeasurementApp {


	//Inner class to represent Feet measurement
	public static class Feet {
		private final double value;

		public Feet(double value) {
			this.value = value;
		}

		public double getValue() {
			return value;
		}

		/**
		 * Overrides equals according to the standard Object contract.
		 * Uses Double.compare for numeric comparison rather than ==.
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true; // reflexive
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false; // null-safe and typed
			}
			Feet other = (Feet) obj;
			return Double.compare(value, other.value) == 0;
		}

		@Override
		public int hashCode() {
			return Objects.hash(value);
		}
	}


	//Inner class to represent Inch measurement
	public static class Inches {

		private final double value;

		public Inches(double value) {
			this.value = value;
		}

		public double getValue() {
			return value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			if (obj == null || getClass() != obj.getClass())
				return false;

			Inches other = (Inches) obj;
			return Double.compare(this.value, other.value) == 0;
		}

		@Override
		public int hashCode() {
			return Objects.hash(value);
		}
	}

	public static void main(String[] args) {

		try (Scanner scanner = new Scanner(System.in)) {

			// FEET INPUT
			System.out.println("Enter first feet value:");
			double feet1 = Double.parseDouble(scanner.nextLine());

			System.out.println("Enter second feet value:");
			double feet2 = Double.parseDouble(scanner.nextLine());

			Feet f1 = new Feet(feet1);
			Feet f2 = new Feet(feet2);

			boolean feetResult = f1.equals(f2);
			System.out.println("Are the two values equal? " + feetResult);

			// INCHES INPUT
			System.out.println("\nEnter first inches value:");
			double inch1 = Double.parseDouble(scanner.nextLine());

			System.out.println("Enter second inches value:");
			double inch2 = Double.parseDouble(scanner.nextLine());

			Inches i1 = new Inches(inch1);
			Inches i2 = new Inches(inch2);

			boolean inchResult = i1.equals(i2);
			System.out.println("Are the two values equal? " + inchResult);

		} catch (NumberFormatException e) {
			System.err.println("Invalid numeric input provided.");
		}
	}
}
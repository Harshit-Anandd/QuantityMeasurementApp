/**
 * QuantityMeasurementApp - UC1: Feet measurement equality
 *
 * This class is responsible for checking the equality of two numerical values
 * measured in feet in the Quantity Measurement Application.
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
	
	public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter first feet value:");
            double v1 = Double.parseDouble(sc.nextLine());

            System.out.println("Enter second feet value:");
            double v2 = Double.parseDouble(sc.nextLine());

            Feet f1 = new Feet(v1);
            Feet f2 = new Feet(v2);

            boolean result = f1.equals(f2);

            System.out.println("Are the two values equal? " + result);
        } catch (NumberFormatException e) {
            System.err.println("Non-numeric input provided.");
        }
    }
}
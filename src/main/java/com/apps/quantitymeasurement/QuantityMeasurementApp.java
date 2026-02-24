/**
 * QuantityMeasurementApp - UC6: Addition of Two Length Units
 *
 * Extends UC5 conversion functionality to support addition of two
 * QuantityLength objects.
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

        public static QuantityLength add(
                QuantityLength first,
                QuantityLength second) {

            if (first == null || second == null)
                throw new IllegalArgumentException("Operands cannot be null.");

            return add(first, second, first.unit);
        }

        public static QuantityLength add(
                QuantityLength first,
                QuantityLength second,
                LengthUnit resultUnit) {

            if (first == null || second == null || resultUnit == null)
                throw new IllegalArgumentException("Operands cannot be null.");

            double sumBase =
                    first.toBaseUnit() + second.toBaseUnit();

            double resultValue =
                    resultUnit.fromBase(sumBase);

            return new QuantityLength(resultValue, resultUnit);
        }

        public static QuantityLength add(
                double v1, LengthUnit u1,
                double v2, LengthUnit u2,
                LengthUnit resultUnit) {

            QuantityLength q1 =
                    new QuantityLength(v1, u1);

            QuantityLength q2 =
                    new QuantityLength(v2, u2);

            return add(q1, q2, resultUnit);
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

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Select Operation:");
        System.out.println("1. Add using objects");
        System.out.println("2. Add with result unit");
        System.out.println("3. Add using raw values");

        int choice = Integer.parseInt(sc.nextLine());

        try {

            switch (choice) {

                case 1 -> {
                    QuantityLength q1 = readQuantity(sc);
                    QuantityLength q2 = readQuantity(sc);
                    System.out.println(
                            QuantityLength.add(q1, q2));
                }

                case 2 -> {
                    QuantityLength q1 = readQuantity(sc);
                    QuantityLength q2 = readQuantity(sc);
                    LengthUnit resultUnit =
                            readUnit(sc, "Result Unit:");
                    System.out.println(
                            QuantityLength.add(q1, q2, resultUnit));
                }

                case 3 -> {
                    System.out.println("Value1:");
                    double v1 = Double.parseDouble(sc.nextLine());
                    LengthUnit u1 =
                            readUnit(sc, "Unit1:");

                    System.out.println("Value2:");
                    double v2 = Double.parseDouble(sc.nextLine());
                    LengthUnit u2 =
                            readUnit(sc, "Unit2:");

                    LengthUnit resultUnit =
                            readUnit(sc, "Result Unit:");

                    System.out.println(
                            QuantityLength.add(v1, u1, v2, u2, resultUnit));
                }

                default -> System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }

    private static QuantityLength readQuantity(Scanner sc) {
        System.out.println("Value:");
        double value = Double.parseDouble(sc.nextLine());
        LengthUnit unit = readUnit(sc, "Unit:");
        return new QuantityLength(value, unit);
    }

    private static LengthUnit readUnit(Scanner sc, String msg) {
        System.out.println(msg + " (feet/inch/yard/cm)");
        String input = sc.nextLine();

        return switch (input.toLowerCase()) {
            case "feet" -> LengthUnit.FEET;
            case "inch", "inches" -> LengthUnit.INCH;
            case "yard", "yards" -> LengthUnit.YARD;
            case "cm", "centimeter", "centimeters" ->
                    LengthUnit.CENTIMETER;
            default -> throw new IllegalArgumentException("Invalid unit.");
        };
    }
}
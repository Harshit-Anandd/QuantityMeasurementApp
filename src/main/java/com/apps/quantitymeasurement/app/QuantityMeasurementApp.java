package com.apps.quantitymeasurement.app;

import com.apps.quantitymeasurement.config.ApplicationConfig;
import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.*;

import java.util.Scanner;

public class QuantityMeasurementApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        QuantityMeasurementController controller =
                ApplicationConfig.getController();

        while (true) {

            System.out.println("\n===== Quantity Measurement System =====");
            System.out.println("1 Compare");
            System.out.println("2 Add");
            System.out.println("3 Subtract");
            System.out.println("4 Divide");
            System.out.println("5 Exit");

            int choice = sc.nextInt();

            if (choice == 5) {
                System.out.println("Exiting...");
                break;
            }

            System.out.println("Enter First Quantity:");
            QuantityDTO q1 = readQuantity();

            System.out.println("Enter Second Quantity:");
            QuantityDTO q2 = readQuantity();

            try {

                switch (choice) {

                    case 1 ->
                            System.out.println("Result: " + controller.compare(q1, q2));

                    case 2 ->
                            System.out.println("Result: " + controller.add(q1, q2));

                    case 3 ->
                            System.out.println("Result: " + controller.subtract(q1, q2));

                    case 4 ->
                            System.out.println("Result: " + controller.divide(q1, q2));

                    default ->
                            System.out.println("Invalid option");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static QuantityDTO readQuantity() {

        System.out.println("Select Measurement Type:");
        System.out.println("1 Length");
        System.out.println("2 Weight");
        System.out.println("3 Volume");
        System.out.println("4 Temperature");

        int type = sc.nextInt();

        System.out.print("Enter value: ");
        double value = sc.nextDouble();

        switch (type) {

            case 1 -> {
                System.out.println("Units: 1 FEET, 2 INCH, 3 YARD, 4 CENTIMETER");
                int unit = sc.nextInt();

                return switch (unit) {
                    case 1 -> new QuantityDTO(value, LengthUnit.FEET);
                    case 2 -> new QuantityDTO(value, LengthUnit.INCH);
                    case 3 -> new QuantityDTO(value, LengthUnit.YARD);
                    case 4 -> new QuantityDTO(value, LengthUnit.CENTIMETER);
                    default -> throw new IllegalArgumentException("Invalid unit");
                };
            }

            case 2 -> {
                System.out.println("Units: 1 KILOGRAM, 2 GRAM, 3 POUND");
                int unit = sc.nextInt();

                return switch (unit) {
                    case 1 -> new QuantityDTO(value, WeightUnit.KILOGRAM);
                    case 2 -> new QuantityDTO(value, WeightUnit.GRAM);
                    case 3 -> new QuantityDTO(value, WeightUnit.POUND);
                    default -> throw new IllegalArgumentException("Invalid unit");
                };
            }

            case 3 -> {
                System.out.println("Units: 1 LITRE, 2 MILLILITRE, 3 GALLON");
                int unit = sc.nextInt();

                return switch (unit) {
                    case 1 -> new QuantityDTO(value, VolumeUnit.LITRE);
                    case 2 -> new QuantityDTO(value, VolumeUnit.MILLILITRE);
                    case 3 -> new QuantityDTO(value, VolumeUnit.GALLON);
                    default -> throw new IllegalArgumentException("Invalid unit");
                };
            }

            case 4 -> {
                System.out.println("Units: 1 KELVIN, 2 CELSIUS, 3 FAHRENHEIT");
                int unit = sc.nextInt();

                return switch (unit) {
                    case 1 -> new QuantityDTO(value, TemperatureUnit.KELVIN);
                    case 2 -> new QuantityDTO(value, TemperatureUnit.CELSIUS);
                    case 3 -> new QuantityDTO(value, TemperatureUnit.FAHRENHEIT);
                    default -> throw new IllegalArgumentException("Invalid unit");
                };
            }

            default -> throw new IllegalArgumentException("Invalid measurement type");
        }
    }
}
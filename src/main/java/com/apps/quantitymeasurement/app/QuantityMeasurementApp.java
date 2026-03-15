package com.apps.quantitymeasurement.app;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.*;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import java.util.Scanner;

public class QuantityMeasurementApp {

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		
		QuantityMeasurementServiceImpl service =
				new QuantityMeasurementServiceImpl();

		QuantityMeasurementController controller =
				new QuantityMeasurementController(service);

		boolean running = true;

		while (running) {

			System.out.println("\n===== Quantity Measurement System =====");
			System.out.println("1. Compare Quantities");
			System.out.println("2. Add Quantities");
			System.out.println("3. Subtract Quantities");
			System.out.println("4. Divide Quantities");
			System.out.println("5. Exit");
			System.out.print("Select option: ");

			int choice = scanner.nextInt();

			switch (choice) {

			case 1:
				compareQuantities(controller);
				break;

			case 2:
				addQuantities(controller);
				break;

			case 3:
				subtractQuantities(controller);
				break;

			case 4:
				divideQuantities(controller);
				break;

			case 5:
				running = false;
				System.out.println("Exiting application...");
				break;

			default:
				System.out.println("Invalid option.");
			}
		}
	}

	private static QuantityDTO readQuantity() {

		System.out.println("\nSelect Measurement Type:");
		System.out.println("1. Length");
		System.out.println("2. Weight");
		System.out.println("3. Volume");
		System.out.println("4. Temperature");

		int type = scanner.nextInt();

		System.out.print("Enter value: ");
		double value = scanner.nextDouble();

		switch (type) {

		case 1:
			System.out.println("Units: 1.FEET 2.INCH 3.YARD 4.CENTIMETER");
			int l = scanner.nextInt();

			LengthUnit lengthUnit = switch (l) {
			case 1 -> LengthUnit.FEET;
			case 2 -> LengthUnit.INCH;
			case 3 -> LengthUnit.YARD;
			case 4 -> LengthUnit.CENTIMETER;
			default -> throw new IllegalArgumentException("Invalid unit");
			};

			return new QuantityDTO(value, lengthUnit);

		case 2:
			System.out.println("Units: 1.KILOGRAM 2.GRAM 3.POUND");
			int w = scanner.nextInt();

			WeightUnit weightUnit = switch (w) {
			case 1 -> WeightUnit.KILOGRAM;
			case 2 -> WeightUnit.GRAM;
			case 3 -> WeightUnit.POUND;
			default -> throw new IllegalArgumentException("Invalid unit");
			};

			return new QuantityDTO(value, weightUnit);

		case 3:
			System.out.println("Units: 1.LITRE 2.MILLILITRE 3.GALLON");
			int v = scanner.nextInt();

			VolumeUnit volumeUnit = switch (v) {
			case 1 -> VolumeUnit.LITRE;
			case 2 -> VolumeUnit.MILLILITRE;
			case 3 -> VolumeUnit.GALLON;
			default -> throw new IllegalArgumentException("Invalid unit");
			};

			return new QuantityDTO(value, volumeUnit);

		case 4:
			System.out.println("Units: 1.KELVIN 2.CELSIUS 3.FAHRENHEIT");
			int t = scanner.nextInt();

			TemperatureUnit temperatureUnit = switch (t) {
			case 1 -> TemperatureUnit.KELVIN;
			case 2 -> TemperatureUnit.CELSIUS;
			case 3 -> TemperatureUnit.FAHRENHEIT;
			default -> throw new IllegalArgumentException("Invalid unit");
			};

			return new QuantityDTO(value, temperatureUnit);

		default:
			throw new IllegalArgumentException("Invalid measurement type");
		}
	}

	private static void compareQuantities(QuantityMeasurementController controller) {

		System.out.println("\nEnter First Quantity");
		QuantityDTO q1 = readQuantity();

		System.out.println("\nEnter Second Quantity");
		QuantityDTO q2 = readQuantity();

		boolean result = controller.compare(q1, q2);

		System.out.println("Comparison Result: " + result);
	}

	private static void addQuantities(QuantityMeasurementController controller) {

		System.out.println("\nEnter First Quantity");
		QuantityDTO q1 = readQuantity();

		System.out.println("\nEnter Second Quantity");
		QuantityDTO q2 = readQuantity();

		double result = controller.add(q1, q2);

		System.out.println("Addition Result (Base Unit): " + result);
	}

	private static void subtractQuantities(QuantityMeasurementController controller) {

		System.out.println("\nEnter First Quantity");
		QuantityDTO q1 = readQuantity();

		System.out.println("\nEnter Second Quantity");
		QuantityDTO q2 = readQuantity();

		double result = controller.subtract(q1, q2);

		System.out.println("Subtraction Result (Base Unit): " + result);
	}

	private static void divideQuantities(QuantityMeasurementController controller) {

		System.out.println("\nEnter First Quantity");
		QuantityDTO q1 = readQuantity();

		System.out.println("\nEnter Second Quantity");
		QuantityDTO q2 = readQuantity();

		double result = controller.divide(q1, q2);

		System.out.println("Division Result: " + result);
	}
}
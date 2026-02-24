package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {
	private static final double DELTA = 0.0001;

	// 1. Feet -> Inches
	@Test
	void testFeetToInch() {
		double result = QuantityMeasurementApp.convert(
				1.0,
				QuantityMeasurementApp.LengthUnit.FEET,
				QuantityMeasurementApp.LengthUnit.INCH
				);
		assertEquals(12.0, result, DELTA);
	}

	// 2. Inches -> Feet
	@Test
	void testInchToFeet() {
		double result = QuantityMeasurementApp.convert(
				12.0,
				QuantityMeasurementApp.LengthUnit.INCH,
				QuantityMeasurementApp.LengthUnit.FEET
				);
		assertEquals(1.0, result, DELTA);
	}

	// 3. Yard -> Feet
	@Test
	void testYardToFeet() {
		double result = QuantityMeasurementApp.convert(
				1.0,
				QuantityMeasurementApp.LengthUnit.YARD,
				QuantityMeasurementApp.LengthUnit.FEET
				);
		assertEquals(3.0, result, DELTA);
	}

	// 4. Feet -> Yard
	@Test
	void testFeetToYard() {
		double result = QuantityMeasurementApp.convert(
				6.0,
				QuantityMeasurementApp.LengthUnit.FEET,
				QuantityMeasurementApp.LengthUnit.YARD
				);
		assertEquals(2.0, result, DELTA);
	}

	// 5. Yard -> Inch
	@Test
	void testYardToInch() {
		double result = QuantityMeasurementApp.convert(
				1.0,
				QuantityMeasurementApp.LengthUnit.YARD,
				QuantityMeasurementApp.LengthUnit.INCH
				);
		assertEquals(36.0, result, DELTA);
	}

	// 6. Inch -> Yard
	@Test
	void testInchToYard() {
		double result = QuantityMeasurementApp.convert(
				36.0,
				QuantityMeasurementApp.LengthUnit.INCH,
				QuantityMeasurementApp.LengthUnit.YARD
				);
		assertEquals(1.0, result, DELTA);
	}

	// 7. Centimeter -> Inch
	@Test
	void testCentimeterToInch() {
		double result = QuantityMeasurementApp.convert(
				2.54,
				QuantityMeasurementApp.LengthUnit.CENTIMETER,
				QuantityMeasurementApp.LengthUnit.INCH
				);
		assertEquals(1.0, result, DELTA);
	}

	// 8. Inch -> Centimeter
	@Test
	void testInchToCentimeter() {
		double result = QuantityMeasurementApp.convert(
				1.0,
				QuantityMeasurementApp.LengthUnit.INCH,
				QuantityMeasurementApp.LengthUnit.CENTIMETER
				);
		assertEquals(2.54, result, DELTA);
	}

	// 9. Feet -> Centimeter
	@Test
	void testFeetToCentimeter() {
		double result = QuantityMeasurementApp.convert(
				1.0,
				QuantityMeasurementApp.LengthUnit.FEET,
				QuantityMeasurementApp.LengthUnit.CENTIMETER
				);
		assertEquals(30.48, result, DELTA);
	}

	// 10. Centimeter -> Feet
	@Test
	void testCentimeterToFeet() {
		double result = QuantityMeasurementApp.convert(
				30.48,
				QuantityMeasurementApp.LengthUnit.CENTIMETER,
				QuantityMeasurementApp.LengthUnit.FEET
				);
		assertEquals(1.0, result, DELTA);
	}

	// 11. Yard -> Centimeter
	@Test
	void testYardToCentimeter() {
		double result = QuantityMeasurementApp.convert(
				1.0,
				QuantityMeasurementApp.LengthUnit.YARD,
				QuantityMeasurementApp.LengthUnit.CENTIMETER
				);
		assertEquals(91.44, result, DELTA);
	}

	// 12. Centimeter -> Yard
	@Test
	void testCentimeterToYard() {
		double result = QuantityMeasurementApp.convert(
				91.44,
				QuantityMeasurementApp.LengthUnit.CENTIMETER,
				QuantityMeasurementApp.LengthUnit.YARD
				);
		assertEquals(1.0, result, DELTA);
	}

	// 13. Identity (Feet -> Feet)
	@Test
	void testFeetToFeetIdentity() {
		double result = QuantityMeasurementApp.convert(
				5.0,
				QuantityMeasurementApp.LengthUnit.FEET,
				QuantityMeasurementApp.LengthUnit.FEET
				);
		assertEquals(5.0, result, DELTA);
	}

	// 14. Identity (Inch -> Inch)
	@Test
	void testInchToInchIdentity() {
		double result = QuantityMeasurementApp.convert(
				10.0,
				QuantityMeasurementApp.LengthUnit.INCH,
				QuantityMeasurementApp.LengthUnit.INCH
				);
		assertEquals(10.0, result, DELTA);
	}

	// 15. Zero conversion
	@Test
	void testZeroConversion() {
		double result = QuantityMeasurementApp.convert(
				0.0,
				QuantityMeasurementApp.LengthUnit.YARD,
				QuantityMeasurementApp.LengthUnit.CENTIMETER
				);
		assertEquals(0.0, result, DELTA);
	}

	// 16. Negative value conversion
	@Test
	void testNegativeConversion() {
		double result = QuantityMeasurementApp.convert(
				-1.0,
				QuantityMeasurementApp.LengthUnit.FEET,
				QuantityMeasurementApp.LengthUnit.INCH
				);
		assertEquals(-12.0, result, DELTA);
	}

	// 17. Large value conversion
	@Test
	void testLargeValueConversion() {
		double result = QuantityMeasurementApp.convert(
				1000.0,
				QuantityMeasurementApp.LengthUnit.YARD,
				QuantityMeasurementApp.LengthUnit.FEET
				);
		assertEquals(3000.0, result, DELTA);
	}

	// 18. Invalid numeric (NaN)
	@Test
	void testInvalidNumeric() {
		assertThrows(IllegalArgumentException.class, () ->
		QuantityMeasurementApp.convert(
				Double.NaN,
				QuantityMeasurementApp.LengthUnit.FEET,
				QuantityMeasurementApp.LengthUnit.INCH
				)
				);
	}

	// 19. Null unit
	@Test
	void testNullUnit() {
		assertThrows(IllegalArgumentException.class, () ->
		QuantityMeasurementApp.convert(
				1.0,
				null,
				QuantityMeasurementApp.LengthUnit.FEET
				)
				);
	}
}
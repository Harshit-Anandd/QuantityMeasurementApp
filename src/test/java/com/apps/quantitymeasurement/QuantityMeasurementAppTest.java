package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {
	@Test
	void testFeetEquality() {

		QuantityMeasurementApp.QuantityLength first =
				new QuantityMeasurementApp.QuantityLength(
						2.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength second =
				new QuantityMeasurementApp.QuantityLength(
						2.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertTrue(first.equals(second));
		assertEquals(first.hashCode(), second.hashCode());
	}

	@Test
	void testInchEquality() {

		QuantityMeasurementApp.QuantityLength first =
				new QuantityMeasurementApp.QuantityLength(
						10.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		QuantityMeasurementApp.QuantityLength second =
				new QuantityMeasurementApp.QuantityLength(
						10.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		assertTrue(first.equals(second));
	}

	@Test
	void testYardEquality() {

		QuantityMeasurementApp.QuantityLength first =
				new QuantityMeasurementApp.QuantityLength(
						3.0,
						QuantityMeasurementApp.LengthUnit.YARD
						);

		QuantityMeasurementApp.QuantityLength second =
				new QuantityMeasurementApp.QuantityLength(
						3.0,
						QuantityMeasurementApp.LengthUnit.YARD
						);

		assertTrue(first.equals(second));
	}

	@Test
	void testCentimeterEquality() {

		QuantityMeasurementApp.QuantityLength first =
				new QuantityMeasurementApp.QuantityLength(
						25.0,
						QuantityMeasurementApp.LengthUnit.CENTIMETER
						);

		QuantityMeasurementApp.QuantityLength second =
				new QuantityMeasurementApp.QuantityLength(
						25.0,
						QuantityMeasurementApp.LengthUnit.CENTIMETER
						);

		assertTrue(first.equals(second));
	}

	@Test
	void testFeetAndInchesEquality() {

		QuantityMeasurementApp.QuantityLength oneFoot =
				new QuantityMeasurementApp.QuantityLength(
						1.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength twelveInches =
				new QuantityMeasurementApp.QuantityLength(
						12.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		assertTrue(oneFoot.equals(twelveInches));
		assertTrue(twelveInches.equals(oneFoot));  // symmetry
	}

	@Test
	void testYardAndFeetEquality() {

		QuantityMeasurementApp.QuantityLength oneYard =
				new QuantityMeasurementApp.QuantityLength(
						1.0,
						QuantityMeasurementApp.LengthUnit.YARD
						);

		QuantityMeasurementApp.QuantityLength threeFeet =
				new QuantityMeasurementApp.QuantityLength(
						3.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertTrue(oneYard.equals(threeFeet));
	}

	@Test
	void testYardAndInchEquality() {

		QuantityMeasurementApp.QuantityLength oneYard =
				new QuantityMeasurementApp.QuantityLength(
						1.0,
						QuantityMeasurementApp.LengthUnit.YARD
						);

		QuantityMeasurementApp.QuantityLength thirtySixInches =
				new QuantityMeasurementApp.QuantityLength(
						36.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		assertTrue(oneYard.equals(thirtySixInches));
	}

	@Test
	void testCentimeterAndInchEquality() {

		QuantityMeasurementApp.QuantityLength twoPointFiveFourCm =
				new QuantityMeasurementApp.QuantityLength(
						2.54,
						QuantityMeasurementApp.LengthUnit.CENTIMETER
						);

		QuantityMeasurementApp.QuantityLength oneInch =
				new QuantityMeasurementApp.QuantityLength(
						1.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		assertTrue(twoPointFiveFourCm.equals(oneInch));
	}

	@Test
	void testDifferentValuesNotEqual() {

		QuantityMeasurementApp.QuantityLength first =
				new QuantityMeasurementApp.QuantityLength(
						1.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength second =
				new QuantityMeasurementApp.QuantityLength(
						2.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertFalse(first.equals(second));
	}

	@Test
	void testFeetAndCentimeterNotEqual() {

		QuantityMeasurementApp.QuantityLength oneFoot =
				new QuantityMeasurementApp.QuantityLength(
						1.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength thirtyCm =
				new QuantityMeasurementApp.QuantityLength(
						30.0,
						QuantityMeasurementApp.LengthUnit.CENTIMETER
						);

		assertFalse(oneFoot.equals(thirtyCm));
	}

	@Test
	void testNullComparison() {

		QuantityMeasurementApp.QuantityLength measurement =
				new QuantityMeasurementApp.QuantityLength(
						5.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		assertFalse(measurement.equals(null));
	}

	@Test
	void testDifferentObjectType() {

		QuantityMeasurementApp.QuantityLength measurement =
				new QuantityMeasurementApp.QuantityLength(
						5.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		String invalidObject = "Invalid";

		assertFalse(measurement.equals(invalidObject));
	}
}
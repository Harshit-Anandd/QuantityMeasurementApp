package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {
	@Test
	void testSameUnitEquality() {

		QuantityMeasurementApp.QuantityLength firstMeasurement =
				new QuantityMeasurementApp.QuantityLength(
						2.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength secondMeasurement =
				new QuantityMeasurementApp.QuantityLength(
						2.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		boolean result = firstMeasurement.equals(secondMeasurement);

		assertTrue(result);
	}

	@Test
	void testCrossUnitEquality() {

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

		boolean result = oneFoot.equals(twelveInches);

		assertTrue(result);
	}

	@Test
	void testInequality() {

		QuantityMeasurementApp.QuantityLength firstMeasurement =
				new QuantityMeasurementApp.QuantityLength(
						1.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength secondMeasurement =
				new QuantityMeasurementApp.QuantityLength(
						2.0,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		boolean result = firstMeasurement.equals(secondMeasurement);

		assertFalse(result);
	}

	@Test
	void testNullComparison() {

		QuantityMeasurementApp.QuantityLength measurement =
				new QuantityMeasurementApp.QuantityLength(
						5.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		boolean result = measurement.equals(null);

		assertFalse(result);
	}

	@Test
	void testDifferentTypeComparison() {

		QuantityMeasurementApp.QuantityLength measurement =
				new QuantityMeasurementApp.QuantityLength(
						5.0,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		String invalidObject = "Invalid Type";

		boolean result = measurement.equals(invalidObject);

		assertFalse(result);
	}
}
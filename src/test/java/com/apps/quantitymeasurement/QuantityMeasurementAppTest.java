package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {

	private static final double DELTA = 0.0001;

	// 1–4 Unit existence
	@Test void testUnit_FEET() {
		assertNotNull(QuantityMeasurementApp.LengthUnit.FEET);
	}

	@Test void testUnit_INCH() {
		assertNotNull(QuantityMeasurementApp.LengthUnit.INCH);
	}

	@Test void testUnit_YARD() {
		assertNotNull(QuantityMeasurementApp.LengthUnit.YARD);
	}

	@Test void testUnit_CENTIMETER() {
		assertNotNull(QuantityMeasurementApp.LengthUnit.CENTIMETER);
	}

	// 5–8 convertToBaseUnit
	@Test void testConvertToBase_Inch() {
		assertEquals(1.0,
				QuantityMeasurementApp.LengthUnit.INCH
				.convertToBaseUnit(12), DELTA);
	}

	@Test void testConvertToBase_Yard() {
		assertEquals(3.0,
				QuantityMeasurementApp.LengthUnit.YARD
				.convertToBaseUnit(1), DELTA);
	}

	@Test void testConvertToBase_Centimeter() {
		assertEquals(1.0,
				QuantityMeasurementApp.LengthUnit.CENTIMETER
				.convertToBaseUnit(30.48), DELTA);
	}

	@Test void testConvertToBase_Feet() {
		assertEquals(5.0,
				QuantityMeasurementApp.LengthUnit.FEET
				.convertToBaseUnit(5), DELTA);
	}

	// 9–12 convertFromBaseUnit
	@Test void testConvertFromBase_Inch() {
		assertEquals(12.0,
				QuantityMeasurementApp.LengthUnit.INCH
				.convertFromBaseUnit(1), DELTA);
	}

	@Test void testConvertFromBase_Yard() {
		assertEquals(1.0,
				QuantityMeasurementApp.LengthUnit.YARD
				.convertFromBaseUnit(3), DELTA);
	}

	@Test void testConvertFromBase_Centimeter() {
		assertEquals(30.48,
				QuantityMeasurementApp.LengthUnit.CENTIMETER
				.convertFromBaseUnit(1), DELTA);
	}

	@Test void testConvertFromBase_Feet() {
		assertEquals(5.0,
				QuantityMeasurementApp.LengthUnit.FEET
				.convertFromBaseUnit(5), DELTA);
	}

	// 13 equality
	@Test void testEquality() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						12,
						QuantityMeasurementApp.LengthUnit.INCH);

		assertEquals(q1, q2);
	}

	// 14–18 addition behavior
	@Test void testAddition_Feet() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(2, result.getValue(), DELTA);
	}

	@Test void testAddition_Commutative() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength r1 =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength r2 =
				QuantityMeasurementApp.QuantityLength.add(
						q2, q1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(r1, r2);
	}

	@Test void testAddition_Zero() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						0,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						5,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(5, result.getValue(), DELTA);
	}

	@Test void testAddition_Negative() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						-1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						2,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(1, result.getValue(), DELTA);
	}

	@Test void testAddition_LargeValues() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1000,
						QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						1000,
						QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.YARD);

		assertEquals(2000, result.getValue(), DELTA);
	}

	// 19–24 validations
	@Test void testNullTarget() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(
						q1, q2, null));
	}

	@Test void testNullFirst() {
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(
						null, q2,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	@Test void testNullSecond() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(
						q1, null,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	@Test void testInvalidConstructorValue() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.QuantityLength(
						Double.NaN,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	@Test void testConvertToBase_Invalid() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.LengthUnit.FEET
				.convertToBaseUnit(Double.NaN));
	}

	@Test void testConvertFromBase_Invalid() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.LengthUnit.FEET
				.convertFromBaseUnit(Double.NaN));
	}

	// 25 round-trip conversion
	@Test void testRoundTripConversion() {
		double base =
				QuantityMeasurementApp.LengthUnit.INCH
				.convertToBaseUnit(24);

		double result =
				QuantityMeasurementApp.LengthUnit.INCH
				.convertFromBaseUnit(base);

		assertEquals(24, result, DELTA);
	}
}
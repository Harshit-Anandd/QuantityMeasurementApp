package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {
	private static final double DELTA = 0.0001;

	// 1
	@Test
	void testFeetPlusFeetDefault() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						2,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(q1, q2);

		assertEquals(3, result.getValue(), DELTA);
	}

	// 2
	@Test
	void testFeetPlusInchDefault() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						12,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(q1, q2);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 3
	@Test
	void testInchPlusFeetResultInInch() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						12,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(q1, q2);

		assertEquals(24, result.getValue(), DELTA);
	}

	// 4
	@Test
	void testAddWithResultUnitYard() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						3,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						3,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1,
						q2,
						QuantityMeasurementApp.LengthUnit.YARD
						);

		assertEquals(2, result.getValue(), DELTA);
		assertEquals(QuantityMeasurementApp.LengthUnit.YARD,
				result.getUnit());
	}

	// 5
	@Test
	void testRawPrimitiveOverload() {

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						1,
						QuantityMeasurementApp.LengthUnit.FEET,
						12,
						QuantityMeasurementApp.LengthUnit.INCH,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 6
	@Test
	void testYardPlusFeet() {

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						1,
						QuantityMeasurementApp.LengthUnit.YARD,
						3,
						QuantityMeasurementApp.LengthUnit.FEET,
						QuantityMeasurementApp.LengthUnit.YARD
						);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 7
	@Test
	void testCmPlusInch() {

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						2.54,
						QuantityMeasurementApp.LengthUnit.CENTIMETER,
						1,
						QuantityMeasurementApp.LengthUnit.INCH,
						QuantityMeasurementApp.LengthUnit.CENTIMETER
						);

		assertEquals(5.08, result.getValue(), DELTA);
	}

	// 8
	@Test
	void testZeroAddition() {

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						0,
						QuantityMeasurementApp.LengthUnit.FEET,
						5,
						QuantityMeasurementApp.LengthUnit.FEET,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertEquals(5, result.getValue(), DELTA);
	}

	// 9
	@Test
	void testNegativeAddition() {

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						-1,
						QuantityMeasurementApp.LengthUnit.FEET,
						2,
						QuantityMeasurementApp.LengthUnit.FEET,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertEquals(1, result.getValue(), DELTA);
	}

	// 10
	@Test
	void testLargeValues() {

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						1000,
						QuantityMeasurementApp.LengthUnit.YARD,
						1000,
						QuantityMeasurementApp.LengthUnit.YARD,
						QuantityMeasurementApp.LengthUnit.YARD
						);

		assertEquals(2000, result.getValue(), DELTA);
	}

	// 11
	@Test
	void testCommutativityBaseComparison() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						12,
						QuantityMeasurementApp.LengthUnit.INCH
						);

		QuantityMeasurementApp.QuantityLength r1 =
				QuantityMeasurementApp.QuantityLength.add(q1, q2);

		QuantityMeasurementApp.QuantityLength r2 =
				QuantityMeasurementApp.QuantityLength.add(q2, q1);

		assertEquals(r1, r2);
	}

	// 12
	@Test
	void testNullOperand() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(q1, null));
	}

	// 13
	@Test
	void testNullUnitInOverload() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET
						);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(
						q1,
						q2,
						null));
	}

	// 14
	@Test
	void testInvalidConstructorValue() {

		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.QuantityLength(
						Double.NaN,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	// 15
	@Test
	void testInfinityConstructorValue() {

		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.QuantityLength(
						Double.POSITIVE_INFINITY,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	// 16
	@Test
	void testHashCodeConsistency() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(q1.hashCode(), q2.hashCode());
	}

	// 17
	@Test
	void testAddWithExplicitResultUnitFeet() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						3,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1,
						q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(6, result.getValue(), DELTA);
	}

	// 18
	@Test
	void testRawPrimitiveWithCentimeterResult() {

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						1,
						QuantityMeasurementApp.LengthUnit.INCH,
						2.54,
						QuantityMeasurementApp.LengthUnit.CENTIMETER,
						QuantityMeasurementApp.LengthUnit.CENTIMETER);

		assertEquals(5.08, result.getValue(), DELTA);
	}

	// 19
	@Test
	void testIdentityAddition() {

		QuantityMeasurementApp.QuantityLength q =
				new QuantityMeasurementApp.QuantityLength(
						5,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(q, q);

		assertEquals(10, result.getValue(), DELTA);
	}

	// 20
	@Test
	void testResultUnitPreservedInDefaultAdd() {

		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(
						36,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(q1, q2);

		assertEquals(2, result.getValue(), DELTA);
		assertEquals(QuantityMeasurementApp.LengthUnit.YARD,
				result.getUnit());
	}
}
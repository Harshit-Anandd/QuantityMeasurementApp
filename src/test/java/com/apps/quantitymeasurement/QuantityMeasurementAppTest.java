package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {
	private static final double DELTA = 0.0001;

	// 1
	@Test
	void testAddition_ExplicitTargetUnit_Feet() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 2
	@Test
	void testAddition_ExplicitTargetUnit_Inches() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.INCH);

		assertEquals(24, result.getValue(), DELTA);
	}

	// 3
	@Test
	void testAddition_ExplicitTargetUnit_Yards() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.YARD);

		assertEquals(0.6666, result.getValue(), 0.001);
	}

	// 4
	@Test
	void testAddition_ExplicitTargetUnit_Centimeters() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(2.54,
						QuantityMeasurementApp.LengthUnit.CENTIMETER);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.CENTIMETER);

		assertEquals(5.08, result.getValue(), DELTA);
	}

	// 5
	@Test
	void testAddition_TargetSameAsFirstOperand() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.YARD);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(3,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.YARD);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 6
	@Test
	void testAddition_TargetSameAsSecondOperand() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.YARD);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(3,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(6, result.getValue(), DELTA);
	}

	// 7
	@Test
	void testAddition_Commutativity() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(12,
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

	// 8
	@Test
	void testAddition_WithZero() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(0,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(5,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(5, result.getValue(), DELTA);
	}

	// 9
	@Test
	void testAddition_NegativeValues() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(-1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(2,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(1, result.getValue(), DELTA);
	}

	// 10
	@Test
	void testAddition_NullTargetUnit() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(
						q1, q2, null));
	}

	// 11
	@Test
	void testAddition_NullFirstOperand() {
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(
						null, q2,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	// 12
	@Test
	void testAddition_NullSecondOperand() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityLength.add(
						q1, null,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	// 13
	@Test
	void testAddition_LargeToSmallScale() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.YARD);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(36,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.INCH);

		assertEquals(72, result.getValue(), DELTA);
	}

	// 14
	@Test
	void testAddition_SmallToLargeScale() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(36,
						QuantityMeasurementApp.LengthUnit.INCH);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.YARD);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 15
	@Test
	void testAddition_PrecisionTolerance() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(2.54,
						QuantityMeasurementApp.LengthUnit.CENTIMETER);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(2.54,
						QuantityMeasurementApp.LengthUnit.CENTIMETER);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.INCH);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 16
	@Test
	void testAddition_Immutability_FirstOperand() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength.add(
				q1, q2,
				QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(1, q1.getValue());
	}

	// 17
	@Test
	void testAddition_Immutability_SecondOperand() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.QuantityLength.add(
				q1, q2,
				QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(12, q2.getValue());
	}

	// 18
	@Test
	void testAddition_SameUnitsExplicit() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(3,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(4,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(7, result.getValue(), DELTA);
	}

	// 19
	@Test
	void testAddition_CentimeterAndYard() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(91.44,
						QuantityMeasurementApp.LengthUnit.CENTIMETER);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(1,
						QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.YARD);

		assertEquals(2, result.getValue(), DELTA);
	}

	// 20
	@Test
	void testAddition_LargeValues() {
		QuantityMeasurementApp.QuantityLength q1 =
				new QuantityMeasurementApp.QuantityLength(1000,
						QuantityMeasurementApp.LengthUnit.YARD);
		QuantityMeasurementApp.QuantityLength q2 =
				new QuantityMeasurementApp.QuantityLength(1000,
						QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength result =
				QuantityMeasurementApp.QuantityLength.add(
						q1, q2,
						QuantityMeasurementApp.LengthUnit.YARD);

		assertEquals(2000, result.getValue(), DELTA);
	}

	// 21
	@Test
	void testAddition_InvalidConstructorValue() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.QuantityLength(
						Double.NaN,
						QuantityMeasurementApp.LengthUnit.FEET));
	}
}
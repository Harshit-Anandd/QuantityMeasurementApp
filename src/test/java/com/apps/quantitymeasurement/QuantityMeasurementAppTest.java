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

	// 26
	@Test
	void testWeightUnit_KILOGRAM_Exists() {
		assertNotNull(QuantityMeasurementApp.WeightUnit.KILOGRAM);
	}

	// 27
	@Test
	void testWeightUnit_GRAM_Exists() {
		assertNotNull(QuantityMeasurementApp.WeightUnit.GRAM);
	}

	// 28
	@Test
	void testWeightUnit_POUND_Exists() {
		assertNotNull(QuantityMeasurementApp.WeightUnit.POUND);
	}

	// 29
	@Test
	void testGramToKgConversion() {
		assertEquals(1.0,
				QuantityMeasurementApp.WeightUnit.GRAM
				.convertToBaseUnit(1000), DELTA);
	}

	// 30
	@Test
	void testPoundToKgConversion() {
		assertEquals(1.0,
				QuantityMeasurementApp.WeightUnit.POUND
				.convertToBaseUnit(2.20462), 0.01);
	}

	// 31
	@Test
	void testKgToGramConversion() {
		assertEquals(1000,
				QuantityMeasurementApp.WeightUnit.GRAM
				.convertFromBaseUnit(1), DELTA);
	}

	// 32
	@Test
	void testKgToPoundConversion() {
		assertEquals(2.20462,
				QuantityMeasurementApp.WeightUnit.POUND
				.convertFromBaseUnit(1), 0.01);
	}

	// 33
	@Test
	void testWeightEquality_SameUnit() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(2,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(2,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(w1, w2);
	}

	// 34
	@Test
	void testWeightEquality_GramAndPound() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(2.20462,
						QuantityMeasurementApp.WeightUnit.POUND);

		assertEquals(w1, w2);
	}

	// 35
	@Test
	void testWeightZeroEquality() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(0,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(0,
						QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(w1, w2);
	}

	// 36
	@Test
	void testWeightNegativeEquality() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(-1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(-1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(w1, w2);
	}

	// 37
	@Test
	void testWeightAddition_SameUnit() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(2,
				QuantityMeasurementApp.QuantityWeight.add(w1, w2)
				.getValue(), DELTA);
	}

	// 38
	@Test
	void testWeightAddition_CrossUnit() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(2,
				QuantityMeasurementApp.QuantityWeight.add(w1, w2)
				.getValue(), DELTA);
	}

	// 39
	@Test
	void testWeightAddition_TargetPound() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(4.40924,
				QuantityMeasurementApp.QuantityWeight.add(
						w1, w2,
						QuantityMeasurementApp.WeightUnit.POUND)
				.getValue(), 0.01);
	}

	// 40
	@Test
	void testWeightAddition_Zero() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(0,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(5,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(5,
				QuantityMeasurementApp.QuantityWeight.add(w1, w2)
				.getValue(), DELTA);
	}

	// 41
	@Test
	void testWeightAddition_Negative() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(-1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(2,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(1,
				QuantityMeasurementApp.QuantityWeight.add(w1, w2)
				.getValue(), DELTA);
	}

	// 42
	@Test
	void testWeightAddition_LargeValues() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1000,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(1000,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(2000,
				QuantityMeasurementApp.QuantityWeight.add(w1, w2)
				.getValue(), DELTA);
	}

	// 43
	@Test
	void testWeightNullFirst() {
		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertThrows(NullPointerException.class,
				() -> QuantityMeasurementApp.QuantityWeight.add(
						null, w2));
	}

	// 44
	@Test
	void testWeightNullSecond() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityWeight.add(
						w1, null));
	}

	// 45
	@Test
	void testWeightNullTarget() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.QuantityWeight.add(
						w1, w2, null));
	}

	// 46
	@Test
	void testWeightInvalidConstructor() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.QuantityWeight(
						Double.NaN,
						QuantityMeasurementApp.WeightUnit.KILOGRAM));
	}

	// 47
	@Test
	void testWeightHashCodeConsistency() {
		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(w1.hashCode(), w2.hashCode());
	}

	// 48
	@Test
	void testWeightRoundTrip() {
		double base =
				QuantityMeasurementApp.WeightUnit.POUND
				.convertToBaseUnit(5);

		double result =
				QuantityMeasurementApp.WeightUnit.POUND
				.convertFromBaseUnit(base);

		assertEquals(5, result, DELTA);
	}

	// 49
	@Test
	void testWeightNotEqualsLength() {
		QuantityMeasurementApp.QuantityLength l =
				new QuantityMeasurementApp.QuantityLength(
						1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityWeight w =
				new QuantityMeasurementApp.QuantityWeight(
						1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertNotEquals(l, w);
	}

	//50
	@Test
	void testWeightEquality_ReflexiveProperty() {

		QuantityMeasurementApp.QuantityWeight weight =
				new QuantityMeasurementApp.QuantityWeight(
						5,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(weight, weight);
	}

	//51
	@Test
	void testWeightEquality_SymmetricProperty() {

		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(
						1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(
						1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(w1, w2);
		assertEquals(w2, w1);
	}

	//52
	@Test
	void testWeightEquality_TransitiveProperty() {

		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(
						1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(
						1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		QuantityMeasurementApp.QuantityWeight w3 =
				new QuantityMeasurementApp.QuantityWeight(
						2.20462,
						QuantityMeasurementApp.WeightUnit.POUND);

		assertEquals(w1, w2);
		assertEquals(w2, w3);
		assertEquals(w1, w3);
	}

	//53
	@Test
	void testWeightAddition_RoundTripStability() {

		QuantityMeasurementApp.QuantityWeight w1 =
				new QuantityMeasurementApp.QuantityWeight(
						2,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.QuantityWeight w2 =
				new QuantityMeasurementApp.QuantityWeight(
						500,
						QuantityMeasurementApp.WeightUnit.GRAM);

		QuantityMeasurementApp.QuantityWeight result =
				QuantityMeasurementApp.QuantityWeight.add(
						w1, w2,
						QuantityMeasurementApp.WeightUnit.GRAM);

		// Convert back to kilograms and verify
		QuantityMeasurementApp.QuantityWeight backToKg =
		new QuantityMeasurementApp.QuantityWeight(
				result.getValue(),
				QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(
				2.5,
				QuantityMeasurementApp.WeightUnit.GRAM
				.convertToBaseUnit(backToKg.getValue()),
				DELTA);
	}
}
package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;


public class QuantityMeasurementAppTest {

	private static final double DELTA = 0.0001;

	// =========================================================
	// 1–4 Interface & Structure
	// =========================================================

	@Test
	void test1_LengthUnitImplementsIMeasurable() {
		assertTrue(QuantityMeasurementApp.LengthUnit.FEET
				instanceof QuantityMeasurementApp.IMeasurable);
	}

	@Test
	void test2_WeightUnitImplementsIMeasurable() {
		assertTrue(QuantityMeasurementApp.WeightUnit.KILOGRAM
				instanceof QuantityMeasurementApp.IMeasurable);
	}

	@Test
	void test3_LengthUnitHasConversionFactor() {
		assertTrue(QuantityMeasurementApp.LengthUnit.FEET
				.getConversionFactor() > 0);
	}

	@Test
	void test4_WeightUnitHasConversionFactor() {
		assertTrue(QuantityMeasurementApp.WeightUnit.GRAM
				.getConversionFactor() > 0);
	}

	// =========================================================
	// 5–10 Length Functionality
	// =========================================================

	@Test
	void test5_LengthEquality_CrossUnit() {
		var q1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.LengthUnit.FEET);
		var q2 = new QuantityMeasurementApp.Quantity<>(
				12, QuantityMeasurementApp.LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void test6_LengthConversion() {
		var q = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(12,
				q.convertTo(QuantityMeasurementApp.LengthUnit.INCH)
				.getValue(), DELTA);
	}

	@Test
	void test7_LengthAddition_DefaultUnit() {
		var q1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.LengthUnit.FEET);
		var q2 = new QuantityMeasurementApp.Quantity<>(
				12, QuantityMeasurementApp.LengthUnit.INCH);
		assertEquals(2, q1.add(q2).getValue(), DELTA);
	}

	@Test
	void test8_LengthAddition_TargetUnit() {
		var q1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.LengthUnit.FEET);
		var q2 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(24,
				q1.add(q2, QuantityMeasurementApp.LengthUnit.INCH)
				.getValue(), DELTA);
	}

	@Test
	void test9_LengthZeroEquality() {
		var q1 = new QuantityMeasurementApp.Quantity<>(
				0, QuantityMeasurementApp.LengthUnit.FEET);
		var q2 = new QuantityMeasurementApp.Quantity<>(
				0, QuantityMeasurementApp.LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void test10_LengthNegativeEquality() {
		var q1 = new QuantityMeasurementApp.Quantity<>(
				-1, QuantityMeasurementApp.LengthUnit.FEET);
		var q2 = new QuantityMeasurementApp.Quantity<>(
				-12, QuantityMeasurementApp.LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	// =========================================================
	// 11–16 Weight Functionality
	// =========================================================

	@Test
	void test11_WeightEquality_CrossUnit() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.WeightUnit.GRAM);
		assertEquals(w1, w2);
	}

	@Test
	void test12_WeightConversion() {
		var w = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertEquals(1000,
				w.convertTo(QuantityMeasurementApp.WeightUnit.GRAM)
				.getValue(), DELTA);
	}

	@Test
	void test13_WeightAddition_DefaultUnit() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.WeightUnit.GRAM);
		assertEquals(2, w1.add(w2).getValue(), DELTA);
	}

	@Test
	void test14_WeightAddition_TargetUnit() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertEquals(2000,
				w1.add(w2, QuantityMeasurementApp.WeightUnit.GRAM)
				.getValue(), DELTA);
	}

	@Test
	void test15_WeightZeroEquality() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				0, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				0, QuantityMeasurementApp.WeightUnit.GRAM);
		assertEquals(w1, w2);
	}

	@Test
	void test16_WeightNegativeEquality() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				-1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				-1000, QuantityMeasurementApp.WeightUnit.GRAM);
		assertEquals(w1, w2);
	}

	// =========================================================
	// 17–21 Equality Contract
	// =========================================================

	@Test
	void test17_Reflexive() {
		var w = new QuantityMeasurementApp.Quantity<>(
				5, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertEquals(w, w);
	}

	@Test
	void test18_Symmetric() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.WeightUnit.GRAM);
		assertEquals(w1, w2);
		assertEquals(w2, w1);
	}

	@Test
	void test19_Transitive() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.WeightUnit.GRAM);
		var w3 = new QuantityMeasurementApp.Quantity<>(
				2.20462, QuantityMeasurementApp.WeightUnit.POUND);
		assertEquals(w1, w2);
		assertEquals(w2, w3);
		assertEquals(w1, w3);
	}

	@Test
	void test20_HashCodeConsistency() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.WeightUnit.GRAM);
		assertEquals(w1.hashCode(), w2.hashCode());
	}

	@Test
	void test21_LengthNotEqualWeight() {
		var length = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.LengthUnit.FEET);
		var weight = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertNotEquals(length, weight);
	}

	// =========================================================
	// 22–30 Validation
	// =========================================================

	@Test void test22_NullUnitConstructor() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.Quantity<>(1, null));
	}

	@Test void test23_InvalidConstructorValue() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.Quantity<>(
						Double.NaN,
						QuantityMeasurementApp.WeightUnit.KILOGRAM));
	}

	@Test void test24_AddNullOperand() {
		var w = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertThrows(IllegalArgumentException.class,
				() -> w.add(null));
	}

	@Test void test25_AddNullTargetUnit() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertThrows(IllegalArgumentException.class,
				() -> w1.add(w2, null));
	}

	@Test void test26_ConvertNullTarget() {
		var w = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertThrows(IllegalArgumentException.class,
				() -> w.convertTo(null));
	}

	@Test void test27_InvalidConvertToBase() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.WeightUnit.KILOGRAM
				.convertToBaseUnit(Double.NaN));
	}

	@Test void test28_InvalidConvertFromBase() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.WeightUnit.KILOGRAM
				.convertFromBaseUnit(Double.NaN));
	}

	@Test void test29_LargeAddition() {
		var w1 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		var w2 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertEquals(2000, w1.add(w2).getValue(), DELTA);
	}

	@Test void test30_LengthLargeAddition() {
		var l1 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.LengthUnit.YARD);
		var l2 = new QuantityMeasurementApp.Quantity<>(
				1000, QuantityMeasurementApp.LengthUnit.YARD);
		assertEquals(2000, l1.add(l2).getValue(), DELTA);
	}

	// =========================================================
	// 31–35 Round Trip & Stability
	// =========================================================

	@Test void test31_WeightRoundTrip() {
		var w = new QuantityMeasurementApp.Quantity<>(
				5, QuantityMeasurementApp.WeightUnit.POUND);
		var back = w.convertTo(
				QuantityMeasurementApp.WeightUnit.KILOGRAM)
				.convertTo(
						QuantityMeasurementApp.WeightUnit.POUND);
		assertEquals(w.getValue(), back.getValue(), DELTA);
	}

	@Test void test32_LengthRoundTrip() {
		var l = new QuantityMeasurementApp.Quantity<>(
				5, QuantityMeasurementApp.LengthUnit.YARD);
		var back = l.convertTo(
				QuantityMeasurementApp.LengthUnit.FEET)
				.convertTo(
						QuantityMeasurementApp.LengthUnit.YARD);
		assertEquals(l.getValue(), back.getValue(), DELTA);
	}

	@Test void test33_ToStringNotNull() {
		var w = new QuantityMeasurementApp.Quantity<>(
				1, QuantityMeasurementApp.WeightUnit.KILOGRAM);
		assertNotNull(w.toString());
	}

	@Test void test34_GetUnitName() {
		assertEquals("KILOGRAM",
				QuantityMeasurementApp.WeightUnit.KILOGRAM
				.getUnitName());
	}

	@Test void test35_GetConversionFactor() {
		assertEquals(1.0,
				QuantityMeasurementApp.WeightUnit.KILOGRAM
				.getConversionFactor());
	}
}
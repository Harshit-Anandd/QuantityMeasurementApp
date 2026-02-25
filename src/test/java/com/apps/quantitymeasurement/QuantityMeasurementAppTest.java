package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.apps.quantitymeasurement.QuantityMeasurementApp.*;

public class QuantityMeasurementAppTest {

	private static final double DELTA = 0.001;

	// ---------------- SUBTRACTION TESTS ----------------

	@Test
	public void subtractSameLengthUnit() {

		double value1 = 10.0;
		double value2 = 5.0;

		Quantity<LengthUnit> first = new Quantity<>(value1, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(value2, LengthUnit.FEET);

		Quantity<LengthUnit> result = first.subtract(second);

		assertEquals(5.0, result.getValue(), DELTA);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	public void subtractSameVolumeUnit() {

		double value1 = 10.0;
		double value2 = 3.0;

		Quantity<VolumeUnit> first = new Quantity<>(value1, VolumeUnit.LITRE);
		Quantity<VolumeUnit> second = new Quantity<>(value2, VolumeUnit.LITRE);

		Quantity<VolumeUnit> result = first.subtract(second);

		assertEquals(7.0, result.getValue(), DELTA);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void subtractCrossLengthUnits() {

		double feet = 10.0;
		double inches = 6.0;

		Quantity<LengthUnit> first = new Quantity<>(feet, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(inches, LengthUnit.INCH);

		Quantity<LengthUnit> result = first.subtract(second);

		assertEquals(9.5, result.getValue(), DELTA);
	}

	@Test
	public void subtractWithTargetUnitFeet() {

		double feet = 10.0;
		double inches = 6.0;

		Quantity<LengthUnit> first = new Quantity<>(feet, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(inches, LengthUnit.INCH);

		Quantity<LengthUnit> result = first.subtract(second, LengthUnit.FEET);

		assertEquals(9.5, result.getValue(), DELTA);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	public void subtractWithTargetUnitInches() {

		double feet = 10.0;
		double inches = 6.0;

		Quantity<LengthUnit> first = new Quantity<>(feet, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(inches, LengthUnit.INCH);

		Quantity<LengthUnit> result = first.subtract(second, LengthUnit.INCH);

		assertEquals(114.0, result.getValue(), DELTA);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	@Test
	public void subtractNegativeResult() {

		double small = 5.0;
		double large = 10.0;

		Quantity<LengthUnit> first = new Quantity<>(small, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(large, LengthUnit.FEET);

		Quantity<LengthUnit> result = first.subtract(second);

		assertEquals(-5.0, result.getValue(), DELTA);
	}

	@Test
	public void subtractZeroResult() {

		double feet = 10.0;
		double inches = 120.0;

		Quantity<LengthUnit> first = new Quantity<>(feet, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(inches, LengthUnit.INCH);

		Quantity<LengthUnit> result = first.subtract(second);

		assertEquals(0.0, result.getValue(), DELTA);
	}

	@Test
	public void subtractWithZeroOperand() {

		double value = 5.0;
		double zero = 0.0;

		Quantity<LengthUnit> first = new Quantity<>(value, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(zero, LengthUnit.FEET);

		Quantity<LengthUnit> result = first.subtract(second);

		assertEquals(value, result.getValue(), DELTA);
	}

	@Test
	public void subtractNullOperand() {

		Quantity<LengthUnit> first = new Quantity<>(10.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> first.subtract(null));
	}

	@Test
	public void subtractCrossCategory() {

		Quantity length = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> length.subtract(weight));
	}

	@Test
	public void subtractChainedOperation() {

		Quantity<LengthUnit> first = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(2.0, LengthUnit.FEET);
		Quantity<LengthUnit> third = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> result = first.subtract(second).subtract(third);

		assertEquals(7.0, result.getValue(), DELTA);
	}

	// ---------------- DIVISION TESTS ----------------

	@Test
	public void divideSameLengthUnit() {

		double numerator = 10.0;
		double denominator = 2.0;

		Quantity<LengthUnit> first = new Quantity<>(numerator, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(denominator, LengthUnit.FEET);

		double result = first.divide(second);

		assertEquals(5.0, result, DELTA);
	}

	@Test
	public void divideSameVolumeUnit() {

		double numerator = 10.0;
		double denominator = 5.0;

		Quantity<VolumeUnit> first = new Quantity<>(numerator, VolumeUnit.LITRE);
		Quantity<VolumeUnit> second = new Quantity<>(denominator, VolumeUnit.LITRE);

		double result = first.divide(second);

		assertEquals(2.0, result, DELTA);
	}

	@Test
	public void divideCrossLengthUnits() {

		double inches = 24.0;
		double feet = 2.0;

		Quantity<LengthUnit> first = new Quantity<>(inches, LengthUnit.INCH);
		Quantity<LengthUnit> second = new Quantity<>(feet, LengthUnit.FEET);

		double result = first.divide(second);

		assertEquals(1.0, result, DELTA);
	}

	@Test
	public void divideGreaterThanOne() {

		Quantity<VolumeUnit> first = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> second = new Quantity<>(2.0, VolumeUnit.LITRE);

		double result = first.divide(second);

		assertEquals(2.5, result, DELTA);
	}

	@Test
	public void divideLessThanOne() {

		Quantity<VolumeUnit> first = new Quantity<>(2.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> second = new Quantity<>(5.0, VolumeUnit.LITRE);

		double result = first.divide(second);

		assertEquals(0.4, result, DELTA);
	}

	@Test
	public void divideEqualToOne() {

		Quantity<LengthUnit> first = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(120.0, LengthUnit.INCH);

		double result = first.divide(second);

		assertEquals(1.0, result, DELTA);
	}

	@Test
	public void divideByZero() {

		Quantity<LengthUnit> first = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> second = new Quantity<>(0.0, LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> first.divide(second));
	}

	@Test
	public void divideNullOperand() {

		Quantity<LengthUnit> first = new Quantity<>(10.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> first.divide(null));
	}

	@Test
	public void divideCrossCategory() {

		Quantity length = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> length.divide(weight));
	}
}
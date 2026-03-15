package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.*;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceTest {

	private QuantityMeasurementServiceImpl service;
	private static final double EPSILON = 0.0001;

	@BeforeEach
	void setup() {
		service = new QuantityMeasurementServiceImpl();
	}

	// =====================================================
	// UC1 – Equality (same unit)
	// =====================================================

	@Test
	void given0FeetAnd0Feet_ShouldReturnTrue() {
		assertTrue(service.compare(new QuantityDTO(0, LengthUnit.FEET),
				new QuantityDTO(0, LengthUnit.FEET)));
	}

	@Test
	void given1FeetAnd1Feet_ShouldReturnTrue() {
		assertTrue(service.compare(new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(1, LengthUnit.FEET)));
	}

	@Test
	void given1FeetAnd2Feet_ShouldReturnFalse() {
		assertFalse(service.compare(new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(2, LengthUnit.FEET)));
	}

	// =====================================================
	// UC2 – Null handling
	// =====================================================

	@Test
	void givenNullLeftQuantity_ShouldThrowException() {
		assertThrows(NullPointerException.class, () ->
		service.compare(null, new QuantityDTO(1, LengthUnit.FEET)));
	}

	@Test
	void givenNullRightQuantity_ShouldThrowException() {
		assertThrows(NullPointerException.class, () ->
		service.compare(new QuantityDTO(1, LengthUnit.FEET), null));
	}

	// =====================================================
	// UC3 – Reference equality
	// =====================================================

	@Test
	void givenSameReference_ShouldReturnTrue() {
		QuantityDTO q = new QuantityDTO(5, LengthUnit.FEET);
		assertTrue(service.compare(q, q));
	}

	// =====================================================
	// UC4 – Type safety
	// =====================================================

	@Test
	void givenLengthAndWeight_ShouldReturnFalse() {
		assertFalse(service.compare(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(1, WeightUnit.KILOGRAM)));
	}

	// =====================================================
	// UC5 – Feet / Inch conversion
	// =====================================================

	@Test
	void given1FootAnd12Inch_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(12, LengthUnit.INCH)));
	}

	@Test
	void given2FeetAnd24Inch_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(2, LengthUnit.FEET),
				new QuantityDTO(24, LengthUnit.INCH)));
	}

	@Test
	void given1FootAnd6Inch_ShouldReturnFalse() {
		assertFalse(service.compare(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(6, LengthUnit.INCH)));
	}

	// =====================================================
	// UC6 – Yard conversion
	// =====================================================

	@Test
	void given1YardAnd3Feet_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(1, LengthUnit.YARD),
				new QuantityDTO(3, LengthUnit.FEET)));
	}

	@Test
	void given2YardAnd6Feet_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(2, LengthUnit.YARD),
				new QuantityDTO(6, LengthUnit.FEET)));
	}

	@Test
	void given1YardAnd2Feet_ShouldReturnFalse() {
		assertFalse(service.compare(
				new QuantityDTO(1, LengthUnit.YARD),
				new QuantityDTO(2, LengthUnit.FEET)));
	}

	// =====================================================
	// UC7 – Centimeter conversion
	// =====================================================

	@Test
	void given1FootAnd30_48Cm_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(30.48, LengthUnit.CENTIMETER)));
	}

	@Test
	void given2FeetAnd60_96Cm_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(2, LengthUnit.FEET),
				new QuantityDTO(60.96, LengthUnit.CENTIMETER)));
	}

	@Test
	void given1FeetAnd50Cm_ShouldReturnFalse() {
		assertFalse(service.compare(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(50, LengthUnit.CENTIMETER)));
	}

	// =====================================================
	// UC8 – Weight conversion
	// =====================================================

	@Test
	void given1KgAnd1000Gram_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(1, WeightUnit.KILOGRAM),
				new QuantityDTO(1000, WeightUnit.GRAM)));
	}

	@Test
	void given2KgAnd2000Gram_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(2, WeightUnit.KILOGRAM),
				new QuantityDTO(2000, WeightUnit.GRAM)));
	}

	@Test
	void given1KgAnd500Gram_ShouldReturnFalse() {
		assertFalse(service.compare(
				new QuantityDTO(1, WeightUnit.KILOGRAM),
				new QuantityDTO(500, WeightUnit.GRAM)));
	}

	// =====================================================
	// UC9 – Volume conversion
	// =====================================================

	@Test
	void given1LitreAnd1000ML_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(1, VolumeUnit.LITRE),
				new QuantityDTO(1000, VolumeUnit.MILLILITRE)));
	}

	@Test
	void given2LitreAnd2000ML_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(2, VolumeUnit.LITRE),
				new QuantityDTO(2000, VolumeUnit.MILLILITRE)));
	}

	@Test
	void given1LitreAnd500ML_ShouldReturnFalse() {
		assertFalse(service.compare(
				new QuantityDTO(1, VolumeUnit.LITRE),
				new QuantityDTO(500, VolumeUnit.MILLILITRE)));
	}

	@Test
	void given1GallonAnd3_78541Litre_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(1, VolumeUnit.GALLON),
				new QuantityDTO(3.78541, VolumeUnit.LITRE)));
	}

	// =====================================================
	// UC10 – Addition
	// =====================================================

	@Test
	void given1FootAnd2Feet_WhenAdded_ShouldReturn3() {
		assertEquals(3, service.add(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(2, LengthUnit.FEET)), EPSILON);
	}

	@Test
	void given1FootAnd12Inch_WhenAdded_ShouldReturn2() {
		assertEquals(2, service.add(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(12, LengthUnit.INCH)), EPSILON);
	}

	@Test
	void given1LitreAnd500ML_WhenAdded_ShouldReturn1_5() {
		assertEquals(1.5, service.add(
				new QuantityDTO(1, VolumeUnit.LITRE),
				new QuantityDTO(500, VolumeUnit.MILLILITRE)), EPSILON);
	}

	// =====================================================
	// UC11 – Subtraction
	// =====================================================

	@Test
	void given3FeetAnd1Feet_WhenSubtracted_ShouldReturn2() {
		assertEquals(2, service.subtract(
				new QuantityDTO(3, LengthUnit.FEET),
				new QuantityDTO(1, LengthUnit.FEET)), EPSILON);
	}

	@Test
	void given2FeetAnd24Inch_WhenSubtracted_ShouldReturn0() {
		assertEquals(0, service.subtract(
				new QuantityDTO(2, LengthUnit.FEET),
				new QuantityDTO(24, LengthUnit.INCH)), EPSILON);
	}

	// =====================================================
	// UC12 – Division
	// =====================================================

	@Test
	void given4FeetAnd2Feet_WhenDivided_ShouldReturn2() {
		assertEquals(2, service.divide(
				new QuantityDTO(4, LengthUnit.FEET),
				new QuantityDTO(2, LengthUnit.FEET)), EPSILON);
	}

	@Test
	void given10LitreAnd2Litre_WhenDivided_ShouldReturn5() {
		assertEquals(5, service.divide(
				new QuantityDTO(10, VolumeUnit.LITRE),
				new QuantityDTO(2, VolumeUnit.LITRE)), EPSILON);
	}

	// =====================================================
	// UC14 – Temperature conversion
	// =====================================================

	@Test
	void given0CelsiusAnd273_15Kelvin_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(0, TemperatureUnit.CELSIUS),
				new QuantityDTO(273.15, TemperatureUnit.KELVIN)));
	}

	@Test
	void given32FahrenheitAnd0Celsius_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(32, TemperatureUnit.FAHRENHEIT),
				new QuantityDTO(0, TemperatureUnit.CELSIUS)));
	}

	@Test
	void given212FahrenheitAnd100Celsius_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(212, TemperatureUnit.FAHRENHEIT),
				new QuantityDTO(100, TemperatureUnit.CELSIUS)));
	}

	@Test
	void given100CelsiusAnd373_15Kelvin_ShouldReturnTrue() {
		assertTrue(service.compare(
				new QuantityDTO(100, TemperatureUnit.CELSIUS),
				new QuantityDTO(373.15, TemperatureUnit.KELVIN)));
	}

	@Test
	void given50CelsiusAnd100Celsius_ShouldReturnFalse() {
		assertFalse(service.compare(
				new QuantityDTO(50, TemperatureUnit.CELSIUS),
				new QuantityDTO(100, TemperatureUnit.CELSIUS)));
	}

}
package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.LengthUnit;
import com.apps.quantitymeasurement.repository.QuantityMeasurementRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceTest {

	private QuantityMeasurementServiceImpl service;

	@BeforeEach
	void setup() {

		service = new QuantityMeasurementServiceImpl(
				new QuantityMeasurementRepositoryImpl());
	}

	@Test
	void testCompare_FeetAndInch() {

		QuantityDTO q1 =
				new QuantityDTO(1, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(12, LengthUnit.INCH);

		assertTrue(service.compare(q1,q2));
	}

	@Test
	void testCompare_NotEqual() {

		QuantityDTO q1 =
				new QuantityDTO(1, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(24, LengthUnit.INCH);

		assertFalse(service.compare(q1,q2));
	}

	@Test
	void testAdd_LengthUnits() {

		QuantityDTO q1 =
				new QuantityDTO(1, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(12, LengthUnit.INCH);

		double result = service.add(q1,q2);

		assertEquals(2,result,0.0001);
	}

	@Test
	void testSubtract_LengthUnits() {

		QuantityDTO q1 =
				new QuantityDTO(3, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(1, LengthUnit.FEET);

		double result = service.subtract(q1,q2);

		assertEquals(2,result,0.0001);
	}

	@Test
	void testDivide_LengthUnits() {

		QuantityDTO q1 =
				new QuantityDTO(4, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(2, LengthUnit.FEET);

		double result = service.divide(q1,q2);

		assertEquals(2,result,0.0001);
	}

}
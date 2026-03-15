package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.LengthUnit;
import com.apps.quantitymeasurement.repository.QuantityMeasurementRepositoryImpl;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementControllerTest {

	private QuantityMeasurementController controller;

	@BeforeEach
	void setup() {

		controller =
				new QuantityMeasurementController(
						new QuantityMeasurementServiceImpl(
								new QuantityMeasurementRepositoryImpl()));
	}

	@Test
	void testControllerCompare() {

		QuantityDTO q1 =
				new QuantityDTO(1, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(12, LengthUnit.INCH);

		assertTrue(controller.compare(q1,q2));
	}

	@Test
	void testControllerAdd() {

		QuantityDTO q1 =
				new QuantityDTO(1, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(12, LengthUnit.INCH);

		double result = controller.add(q1,q2);

		assertEquals(2,result,0.0001);
	}

	@Test
	void testControllerSubtract() {

		QuantityDTO q1 =
				new QuantityDTO(3, LengthUnit.FEET);

		QuantityDTO q2 =
				new QuantityDTO(1, LengthUnit.FEET);

		double result = controller.subtract(q1,q2);

		assertEquals(2,result,0.0001);
	}

}
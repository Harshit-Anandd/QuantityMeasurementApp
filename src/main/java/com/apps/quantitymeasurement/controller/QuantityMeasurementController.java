package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

	private final IQuantityMeasurementService service;

	public QuantityMeasurementController(
			IQuantityMeasurementService service) {

		this.service = service;
	}

	public boolean compare(QuantityDTO q1, QuantityDTO q2) {
		return service.compare(q1, q2);
	}

	public double add(QuantityDTO q1, QuantityDTO q2) {
		return service.add(q1, q2);
	}

	public double subtract(QuantityDTO q1, QuantityDTO q2) {
		return service.subtract(q1, q2);
	}

	public double divide(QuantityDTO q1, QuantityDTO q2) {
		return service.divide(q1, q2);
	}
}
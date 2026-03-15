package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;

public class QuantityMeasurementServiceImpl
implements IQuantityMeasurementService {

	private static final double EPSILON = 0.0001;

	public boolean compare(QuantityDTO q1, QuantityDTO q2) {

		if (q1 == null || q2 == null)
			throw new NullPointerException("Quantity cannot be null");

		if (!q1.getUnit().getClass().equals(q2.getUnit().getClass()))
			return false;

		double base1 = q1.getUnit().convertToBaseUnit(q1.getValue());
		double base2 = q2.getUnit().convertToBaseUnit(q2.getValue());

		return Math.abs(base1 - base2) < EPSILON;
	}

	public double add(QuantityDTO q1, QuantityDTO q2) {

		double base1 = q1.getUnit().convertToBaseUnit(q1.getValue());
		double base2 = q2.getUnit().convertToBaseUnit(q2.getValue());

		return base1 + base2;
	}

	public double subtract(QuantityDTO q1, QuantityDTO q2) {

		double base1 = q1.getUnit().convertToBaseUnit(q1.getValue());
		double base2 = q2.getUnit().convertToBaseUnit(q2.getValue());

		return base1 - base2;
	}

	public double divide(QuantityDTO q1, QuantityDTO q2) {

		double base1 = q1.getUnit().convertToBaseUnit(q1.getValue());
		double base2 = q2.getUnit().convertToBaseUnit(q2.getValue());

		return base1 / base2;
	}
}
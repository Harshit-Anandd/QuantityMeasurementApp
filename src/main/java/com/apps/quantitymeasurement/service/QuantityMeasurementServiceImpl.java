package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private static final double EPSILON = 0.0001;

	private final IQuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(
			IQuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	private void validateTypes(QuantityDTO q1, QuantityDTO q2) {

		if (!q1.getUnit().getClass()
				.equals(q2.getUnit().getClass()))
			throw new QuantityMeasurementException(
					"Incompatible measurement types.");
	}

	public boolean compare(QuantityDTO q1, QuantityDTO q2) {

		validateTypes(q1, q2);

		double base1 = q1.getUnit().convertToBaseUnit(q1.getValue());
		double base2 = q2.getUnit().convertToBaseUnit(q2.getValue());

		return Math.abs(base1 - base2) < EPSILON;
	}

	public double add(QuantityDTO q1, QuantityDTO q2) {

		validateTypes(q1, q2);

		double result =
				q1.getUnit().convertToBaseUnit(q1.getValue())
				+ q2.getUnit().convertToBaseUnit(q2.getValue());

		repository.saveOperation(new QuantityMeasurementEntity(
				"ADD",
				q1.getValue()+" "+q1.getUnit().getUnitName(),
				q2.getValue()+" "+q2.getUnit().getUnitName(),
				String.valueOf(result)));

		return result;
	}

	public double subtract(QuantityDTO q1, QuantityDTO q2) {

		validateTypes(q1, q2);

		double result =
				q1.getUnit().convertToBaseUnit(q1.getValue())
				- q2.getUnit().convertToBaseUnit(q2.getValue());

		repository.saveOperation(new QuantityMeasurementEntity(
				"SUBTRACT",
				q1.getValue()+" "+q1.getUnit().getUnitName(),
				q2.getValue()+" "+q2.getUnit().getUnitName(),
				String.valueOf(result)));

		return result;
	}

	public double divide(QuantityDTO q1, QuantityDTO q2) {

		validateTypes(q1, q2);

		double base2 =
				q2.getUnit().convertToBaseUnit(q2.getValue());

		if(Math.abs(base2) < EPSILON)
			throw new QuantityMeasurementException("Division by zero");

		double result =
				q1.getUnit().convertToBaseUnit(q1.getValue()) / base2;

		repository.saveOperation(new QuantityMeasurementEntity(
				"DIVIDE",
				q1.getValue()+" "+q1.getUnit().getUnitName(),
				q2.getValue()+" "+q2.getUnit().getUnitName(),
				String.valueOf(result)));

		return result;
	}
}
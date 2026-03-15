package com.apps.quantitymeasurement.dto;

import com.apps.quantitymeasurement.model.IMeasurable;

public class QuantityDTO {

	private final double value;
	private final IMeasurable unit;

	public QuantityDTO(double value, IMeasurable unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public IMeasurable getUnit() {
		return unit;
	}
}
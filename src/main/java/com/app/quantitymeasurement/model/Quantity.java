package com.app.quantitymeasurement.model;

public class Quantity<U extends IMeasurable> {

	private final double value;
	private final U unit;

	public Quantity(double value, U unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() { 
		return value; 
	}

	public U getUnit() { 
		return unit; 
	}

	public double toBase() {
		return unit.convertToBaseUnit(value);
	}
}
package com.app.quantitymeasurement.model;

public enum WeightUnit implements IMeasurable {

	KILOGRAM(1),
	GRAM(0.001),
	POUND(0.453592);

	private final double factor;

	WeightUnit(double factor) {
		this.factor = factor;
	}

	public double convertToBaseUnit(double value) {
		return value * factor;
	}

	public double convertFromBaseUnit(double baseValue) {
		return baseValue / factor;
	}

	public double getConversionFactor() { 
		return factor; 
	}

	public String getUnitName() { 
		return name(); 
	}
}
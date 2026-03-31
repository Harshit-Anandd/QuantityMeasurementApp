package com.app.quantitymeasurement.model;

public enum WeightUnit implements IMeasurable {

	MILLIGRAM(0.000001),
	GRAM(0.001),
	KILOGRAM(1),
	POUND(0.453592),
	TONNE(1000);
	
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

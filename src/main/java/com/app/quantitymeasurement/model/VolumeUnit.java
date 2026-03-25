package com.app.quantitymeasurement.model;

public enum VolumeUnit implements IMeasurable {

	LITRE(1),
	MILLILITER(0.001),
	GALLON(3.785);

	private final double factor;

	VolumeUnit(double factor) {
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

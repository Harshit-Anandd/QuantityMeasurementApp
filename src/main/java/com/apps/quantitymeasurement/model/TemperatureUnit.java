package com.apps.quantitymeasurement.model;

public enum TemperatureUnit implements IMeasurable {

	KELVIN,
	CELSIUS,
	FAHRENHEIT;

	public double convertToBaseUnit(double value) {

		switch (this) {
		case KELVIN:
			return value;
		case CELSIUS:
			return value + 273.15;
		case FAHRENHEIT:
			return (value - 32) * 5 / 9 + 273.15;
		}

		throw new IllegalStateException();
	}

	public double convertFromBaseUnit(double baseValue) {

		switch (this) {
		case KELVIN:
			return baseValue;
		case CELSIUS:
			return baseValue - 273.15;
		case FAHRENHEIT:
			return (baseValue - 273.15) * 9 / 5 + 32;
		}

		throw new IllegalStateException();
	}

	public double getConversionFactor() {
		throw new UnsupportedOperationException(
				"Temperature uses offset conversion.");
	}

	public String getUnitName() {
		return name();
	}

	@Override
	public boolean supportsArithmetic() {
		return false;
	}
}
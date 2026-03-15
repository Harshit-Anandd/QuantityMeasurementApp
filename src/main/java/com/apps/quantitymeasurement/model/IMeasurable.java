package com.apps.quantitymeasurement.model;

public interface IMeasurable {

	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double baseValue);

	double getConversionFactor();

	String getUnitName();

	default boolean supportsArithmetic() {
		return true;
	}
}
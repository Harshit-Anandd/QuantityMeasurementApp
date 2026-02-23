package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {
	@Test
    void testEquality_SameValue() {
        QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet b = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(a.equals(b));
    }

    @Test
    void testEquality_DifferentValue() {
    	QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
    	QuantityMeasurementApp.Feet b = new QuantityMeasurementApp.Feet(2.0);
        assertFalse(a.equals(b));
    }

    @Test
    void testEquality_NullComparison() {
    	QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(a.equals(null));
    }

    @Test
    void testEquality_SameReference() {
    	QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(a.equals(a));
    }

    @Test
    void testEquality_NonNumericInputHandled() {
        // although input parsing lives in app, equals should not throw for other objects
    	QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(a.equals("not a Feet"));
    }
    
    @Test
    void testEqualInches() {
    	QuantityMeasurementApp.Inches firstMeasurement = new QuantityMeasurementApp.Inches(10.0);
    	QuantityMeasurementApp.Inches secondMeasurement = new QuantityMeasurementApp.Inches(10.0);

        boolean result = firstMeasurement.equals(secondMeasurement);

        assertTrue(result);
    }

    @Test
    void testDifferentInches() {
    	QuantityMeasurementApp.Inches firstMeasurement = new QuantityMeasurementApp.Inches(10.0);
    	QuantityMeasurementApp.Inches secondMeasurement = new QuantityMeasurementApp.Inches(12.0);

        boolean result = firstMeasurement.equals(secondMeasurement);

        assertFalse(result);
    }

    @Test
    void testNullComparison() {
    	QuantityMeasurementApp.Inches measurement = new QuantityMeasurementApp.Inches(10.0);

        boolean result = measurement.equals(null);

        assertFalse(result);
    }

    @Test
    void testDifferentTypeComparison() {
    	QuantityMeasurementApp.Inches inchesMeasurement = new QuantityMeasurementApp.Inches(10.0);
    	QuantityMeasurementApp.Feet feetMeasurement = new QuantityMeasurementApp.Feet(10.0);

        boolean result = inchesMeasurement.equals(feetMeasurement);

        assertFalse(result);
    }

    @Test
    void testSameReference() {
    	QuantityMeasurementApp.Inches measurement = new QuantityMeasurementApp.Inches(7.0);

        boolean result = measurement.equals(measurement);

        assertTrue(result);
    }
}
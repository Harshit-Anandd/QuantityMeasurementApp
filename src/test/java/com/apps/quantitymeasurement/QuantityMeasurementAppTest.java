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
}
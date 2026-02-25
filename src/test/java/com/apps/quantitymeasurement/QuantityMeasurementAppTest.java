package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.QuantityMeasurementApp.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.0001;

    // =========================================================
    // 1–7: Equality Tests
    // =========================================================

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(0.0, TemperatureUnit.CELSIUS)
        );
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertEquals(
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        assertEquals(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        assertEquals(
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS),
                new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> a =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> b =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> temp =
                new Quantity<>(25.0, TemperatureUnit.CELSIUS);

        assertEquals(temp, temp);
    }

    // =========================================================
    // 8–14: Conversion Tests
    // =========================================================

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {

        Quantity<TemperatureUnit> temp =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        double result =
                TemperatureUnit.FAHRENHEIT.convertFromBaseUnit(
                        temp.getUnit().convertToBaseUnit(temp.getValue())
                );

        assertEquals(122.0, result, EPSILON);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {

        Quantity<TemperatureUnit> temp =
                new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT);

        double result =
                TemperatureUnit.CELSIUS.convertFromBaseUnit(
                        temp.getUnit().convertToBaseUnit(temp.getValue())
                );

        assertEquals(50.0, result, EPSILON);
    }

    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {

        double original = 75.0;

        double base =
                TemperatureUnit.CELSIUS.convertToBaseUnit(original);

        double converted =
                TemperatureUnit.FAHRENHEIT.convertFromBaseUnit(base);

        double roundTrip =
                TemperatureUnit.CELSIUS.convertFromBaseUnit(
                        TemperatureUnit.FAHRENHEIT.convertToBaseUnit(converted)
                );

        assertEquals(original, roundTrip, EPSILON);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        double value = 30.0;

        double base =
                TemperatureUnit.CELSIUS.convertToBaseUnit(value);

        double result =
                TemperatureUnit.CELSIUS.convertFromBaseUnit(base);

        assertEquals(value, result, EPSILON);
    }

    @Test
    void testTemperatureConversion_ZeroValue() {
        assertEquals(
                32.0,
                TemperatureUnit.FAHRENHEIT.convertFromBaseUnit(
                        TemperatureUnit.CELSIUS.convertToBaseUnit(0.0)
                ),
                EPSILON
        );
    }

    @Test
    void testTemperatureConversion_NegativeValues() {
        assertEquals(
                -4.0,
                TemperatureUnit.FAHRENHEIT.convertFromBaseUnit(
                        TemperatureUnit.CELSIUS.convertToBaseUnit(-20.0)
                ),
                EPSILON
        );
    }

    @Test
    void testTemperatureConversion_LargeValues() {
        assertEquals(
                1832.0,
                TemperatureUnit.FAHRENHEIT.convertFromBaseUnit(
                        TemperatureUnit.CELSIUS.convertToBaseUnit(1000.0)
                ),
                EPSILON
        );
    }

    // =========================================================
    // 15–18: Unsupported Operations
    // =========================================================

    @Test
    void testTemperatureUnsupportedOperation_Add() {

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        UnsupportedOperationException ex =
                assertThrows(UnsupportedOperationException.class,
                        () -> t1.add(t2));

        assertTrue(ex.getMessage()
                .contains("Arithmetic operations are not supported"));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_ErrorMessage() {
        UnsupportedOperationException ex =
                assertThrows(UnsupportedOperationException.class,
                        () -> new Quantity<>(1.0, TemperatureUnit.CELSIUS)
                                .add(new Quantity<>(1.0, TemperatureUnit.CELSIUS)));

        assertNotNull(ex.getMessage());
    }

    // =========================================================
    // 19–21: Category Incompatibility
    // =========================================================

    @Test
    void testTemperatureVsLengthIncompatibility() {
        assertNotEquals(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                new Quantity<>(100.0, LengthUnit.FEET)
        );
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        assertNotEquals(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS),
                new Quantity<>(50.0, WeightUnit.KILOGRAM)
        );
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        assertNotEquals(
                new Quantity<>(25.0, TemperatureUnit.CELSIUS),
                new Quantity<>(25.0, VolumeUnit.LITRE)
        );
    }

    // =========================================================
    // 22–25: Operation Support Methods
    // =========================================================

    @Test
    void testOperationSupportMethods_TemperatureUnitAddition() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_TemperatureUnitDivision() {
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthUnitAddition() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightUnitDivision() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    // =========================================================
    // 26–41: Interface & Integration Validation
    // =========================================================

    @Test
    void testTemperatureEnumImplementsMeasurable() {
        assertTrue(IMeasurable.class
                .isAssignableFrom(TemperatureUnit.class));
    }

    @Test
    void testTemperatureUnit_AllConstants() {
        assertNotNull(TemperatureUnit.CELSIUS);
        assertNotNull(TemperatureUnit.FAHRENHEIT);
        assertNotNull(TemperatureUnit.KELVIN);
    }

    @Test
    void testTemperatureUnit_NameMethod() {
        assertEquals("CELSIUS",
                TemperatureUnit.CELSIUS.getUnitName());
    }

    @Test
    void testTemperatureUnit_ConversionFactor() {
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.getConversionFactor());
    }

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(100.0, null));
    }

    @Test
    void testTemperatureNullOperandValidation_InComparison() {
        Quantity<TemperatureUnit> temp =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        assertFalse(temp.equals(null));
    }

    @Test
    void testTemperatureDifferentValuesInequality() {
        assertNotEquals(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS),
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
        );
    }

    @Test
    void testTemperatureConversionPrecision_Epsilon() {
        Quantity<TemperatureUnit> t1 =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 =
                new Quantity<>(32.00001, TemperatureUnit.FAHRENHEIT);

        assertEquals(t1, t2);
    }

    @Test
    void testTemperatureIntegrationWithGenericQuantity() {
        Quantity<TemperatureUnit> temp =
                new Quantity<>(25.0, TemperatureUnit.CELSIUS);

        assertEquals(25.0, temp.getValue());
        assertEquals(TemperatureUnit.CELSIUS, temp.getUnit());
    }
}
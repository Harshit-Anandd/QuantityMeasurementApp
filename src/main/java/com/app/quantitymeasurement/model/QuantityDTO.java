package com.app.quantitymeasurement.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QuantityDTO {

    public interface IMeasurableUnit {}

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITER, GALLON
    }

    public enum WeightUnit implements IMeasurableUnit {
        MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT
    }

    @NotNull(message = "Value cannot be empty")
    public double value;

    @NotNull(message = "Unit cannot be null")
    public String unit;

    @NotNull(message = "Measurement type cannot be null")
    @Pattern(
        regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
        message = "Invalid measurement type"
    )
    public String measurementType;

    @AssertTrue(message = "Invalid unit for given measurement type")
    public boolean isValidUnit() {
        try {
            switch (measurementType) {
                case "LengthUnit": LengthUnit.valueOf(unit); break;
                case "VolumeUnit": VolumeUnit.valueOf(unit); break;
                case "WeightUnit": WeightUnit.valueOf(unit); break;
                case "TemperatureUnit": TemperatureUnit.valueOf(unit); break;
                default: return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
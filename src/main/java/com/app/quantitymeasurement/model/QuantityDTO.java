package com.app.quantitymeasurement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QuantityDTO {

    @NotNull(message = "Value must not be null")
    private Double value;

    @NotEmpty(message = "Unit must not be empty")
    private String unit;

    @NotEmpty(message = "Measurement type must not be empty")
    @Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
            message = "Measurement type must be LengthUnit, VolumeUnit, WeightUnit, or TemperatureUnit")
    private String measurementType;

    @JsonIgnore
    @jakarta.validation.constraints.AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnit() {
        if (unit == null || measurementType == null) return true;
        try {
            switch (measurementType) {
                case "LengthUnit" -> LengthUnit.valueOf(unit);
                case "VolumeUnit" -> VolumeUnit.valueOf(unit);
                case "WeightUnit" -> WeightUnit.valueOf(unit);
                case "TemperatureUnit" -> TemperatureUnit.valueOf(unit);
                default -> { return false; }
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
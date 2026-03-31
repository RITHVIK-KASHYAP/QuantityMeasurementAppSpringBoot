package com.app.quantitymeasurement.model;

public enum TemperatureUnit implements IMeasurable {
    FAHRENHEIT(1.0),
    CELSIUS(1.0);

    private final double conversionFactor;

    TemperatureUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public String getMeasurementType() {
        return "TemperatureUnit";
    }

    @Override
    public double toBaseUnit(double value) {
        return switch (this) {
            case FAHRENHEIT -> value;
            case CELSIUS -> (value * 9.0 / 5.0) + 32.0;
        };
    }

    @Override
    public double fromBaseUnit(double baseValue) {
        return switch (this) {
            case FAHRENHEIT -> baseValue;
            case CELSIUS -> (baseValue - 32.0) * 5.0 / 9.0;
        };
    }
}
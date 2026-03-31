package com.app.quantitymeasurement.model;

public enum LengthUnit implements IMeasurable {
    FEET(12.0),
    INCHES(1.0),
    YARD(36.0),
    CENTIMETER(0.3937007874);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public String getMeasurementType() {
        return "LengthUnit";
    }
}
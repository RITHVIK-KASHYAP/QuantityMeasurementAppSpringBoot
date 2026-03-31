package com.app.quantitymeasurement.model;

public enum WeightUnit implements IMeasurable {
    KILOGRAM(1000.0),
    GRAM(1.0),
    TONNE(1000000.0);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public String getMeasurementType() {
        return "WeightUnit";
    }
}
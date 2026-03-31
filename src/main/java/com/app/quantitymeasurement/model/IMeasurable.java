package com.app.quantitymeasurement.model;


public interface IMeasurable {
    double getConversionFactor();
    String getMeasurementType();

    default double toBaseUnit(double value) {
        return value * getConversionFactor();
    }

    default double fromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }
}
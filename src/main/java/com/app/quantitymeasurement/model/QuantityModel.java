package com.app.quantitymeasurement.model;

public class QuantityModel<T extends IMeasurable> {
    private double value;
    private T unit;

    public QuantityModel(double value, T unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public T getUnit() {
        return unit;
    }

    public double getBaseValue() {
        return unit.toBaseUnit(value);
    }

    public boolean compare(QuantityModel<? extends IMeasurable> other) {
        if (!this.unit.getMeasurementType().equals(other.getUnit().getMeasurementType())) {
            return false;
        }
        return Math.abs(this.getBaseValue() - other.getBaseValue()) < 0.0001;
    }
}
package com.example.dronedeliveryapp;


import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public final class Weight {
    private String unit;
    private Double value;

    public Weight(String unit, double value) {
        if (value < 0) throw new IllegalStateException("Weight cannot be negative");
        this.unit = unit;
        this.value = value;
    }

    public Weight(Double value) {
        this.value = value;
        this.unit = "grams";
    }

    public Weight() {

    }

    public String unit() {
        return unit;
    }

    public double value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Weight) obj;
        return Objects.equals(this.unit, that.unit) &&
                Double.doubleToLongBits(this.value) == Double.doubleToLongBits(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, value);
    }

    @Override
    public String toString() {
        return "Weight[" +
                "unit=" + unit + ", " +
                "value=" + value + ']';
    }

}

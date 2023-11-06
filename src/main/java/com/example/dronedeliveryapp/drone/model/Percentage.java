package com.example.dronedeliveryapp.drone.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;


@Embeddable
public final class Percentage {
    private Double value;


    public Percentage(double value) {
        if (value < 0 || value > 100) throw new IllegalStateException("Percentage cannot be less than 0 or greater than 100");
        this.value = value;
    }


    public Percentage() {
    }

    public double value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Percentage) obj;
        return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Percentage[" +
                "value=" + value + ']';
    }

}

package com.example.dronedeliveryapp.drone.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class DroneRegistrationResult implements Serializable {
    private String serialNumber;

    public DroneRegistrationResult(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneRegistrationResult() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DroneRegistrationResult that)) return false;
        return Objects.equals(serialNumber, that.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DroneRegistrationResult.class.getSimpleName() + "[", "]")
                .add("serialNumber='" + serialNumber + "'")
                .toString();
    }
}

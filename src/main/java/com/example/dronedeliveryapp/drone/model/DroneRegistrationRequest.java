package com.example.dronedeliveryapp.drone.model;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class DroneRegistrationRequest implements Serializable {

    @Size(max = 100, message = "Serial number length cannot exceed 100")
    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    //    @EnumValidator(enumClazz = DroneModel.class, message = "Not a recognized drone model")
    @NotNull(message = "Drone model is required")
    private DroneModel model;

    @Min(value = 0, message = "Drone max weight cannot go below 0")
    @Max(value = 500, message = "Drone max weight cannot exceed 500")
    @NotNull(message = "WeightInGrams number is required")
    private Double weightInGrams;

    public DroneRegistrationRequest(String serialNumber, DroneModel model, Double weightInGrams) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightInGrams = weightInGrams;
    }

    public DroneRegistrationRequest() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public Double getWeightInGrams() {
        return weightInGrams;
    }

    public void setWeightInGrams(Double weightInGrams) {
        this.weightInGrams = weightInGrams;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DroneRegistrationRequest that)) return false;
        return Objects.equals(serialNumber, that.serialNumber) && Objects.equals(model, that.model) && Objects.equals(weightInGrams, that.weightInGrams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, model, weightInGrams);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DroneRegistrationRequest.class.getSimpleName() + "[", "]")
                .add("serialNumber='" + serialNumber + "'")
                .add("model='" + model + "'")
                .add("weight='" + weightInGrams + "'")
                .toString();
    }
}

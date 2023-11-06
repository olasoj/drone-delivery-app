package com.example.dronedeliveryapp.medication.model;

import java.util.Objects;
import java.util.StringJoiner;

public class MedicationRegistrationRequest {
    private String name;
    private String code;
    private String image;
    private Double weightInGrams;

    public MedicationRegistrationRequest(String name, String code, String image, Double weightInGrams) {
        this.name = name;
        this.code = code;
        this.image = image;
        this.weightInGrams = weightInGrams;
    }

    public MedicationRegistrationRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getWeightInGrams() {
        return weightInGrams;
    }

    public void setWeightInGrams(Double weightInGrams) {
        this.weightInGrams = weightInGrams;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", MedicationRegistrationRequest.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("code='" + code + "'")
                .add("image='" + image + "'")
                .add("weightInGrams=" + weightInGrams)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MedicationRegistrationRequest that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(code, that.code) && Objects.equals(image, that.image) && Objects.equals(weightInGrams, that.weightInGrams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, image, weightInGrams);
    }
}

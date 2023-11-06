package com.example.dronedeliveryapp.medication.model;

import java.util.Objects;
import java.util.StringJoiner;

public class MedicationDTO {

    private String image;
    private String medicationName;
    private String code;
    private Double weightLimit;
    private String weightUnit;

    public MedicationDTO() {
    }

    public MedicationDTO(String image, String medicationName, String code, Double weightLimit, String weightUnit) {
        this.image = image;
        this.medicationName = medicationName;
        this.code = code;
        this.weightLimit = weightLimit;
        this.weightUnit = weightUnit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MedicationDTO that)) return false;
        return Objects.equals(image, that.image) && Objects.equals(medicationName, that.medicationName) && Objects.equals(code, that.code) && Objects.equals(weightLimit, that.weightLimit) && Objects.equals(weightUnit, that.weightUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, medicationName, code, weightLimit, weightUnit);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MedicationDTO.class.getSimpleName() + "[", "]")
                .add("image='" + image + "'")
                .add("medicationName='" + medicationName + "'")
                .add("code='" + code + "'")
                .add("weightLimit=" + weightLimit)
                .add("weightUnit='" + weightUnit + "'")
                .toString();
    }
}

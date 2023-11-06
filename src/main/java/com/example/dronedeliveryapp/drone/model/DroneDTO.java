package com.example.dronedeliveryapp.drone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public interface DroneDTO {

    Long getDroneId();

    String getSerialNumber();

    Instant getUpdatedAt();
    Instant getCreatedAt();

    DroneModel getModel();

    State getState();

    String getWeightUnit();

    Double getWeightLimit();

    Double getBatteryCapacity();

    @JsonIgnore
    int getTotalCount();

}

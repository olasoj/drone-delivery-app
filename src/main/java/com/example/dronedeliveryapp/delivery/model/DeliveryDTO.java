package com.example.dronedeliveryapp.delivery.model;

import com.example.dronedeliveryapp.drone.model.State;

public interface DeliveryDTO {

    String getTrackingId();

    String getSerialNumber();
    State getDroneState();
    Double getBatteryCapacity();

    String getImage();

    String getMedicationName();

    String getCode();

    Double getWeightLimit();

    String getWeightUnit();
    int getTotalCount();
}

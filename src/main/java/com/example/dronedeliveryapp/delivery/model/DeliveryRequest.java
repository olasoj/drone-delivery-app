package com.example.dronedeliveryapp.delivery.model;

import com.example.dronedeliveryapp.medication.model.MedicationRegistrationRequest;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record DeliveryRequest(@NotNull(message = "MedicationRegistrationRequests is required") Set<MedicationRegistrationRequest> medicationRegistrationRequests) {


}

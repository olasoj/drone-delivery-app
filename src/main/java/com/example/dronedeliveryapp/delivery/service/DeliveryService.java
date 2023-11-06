package com.example.dronedeliveryapp.delivery.service;

import com.example.dronedeliveryapp.delivery.model.GetLoadedMedicationResult;
import com.example.dronedeliveryapp.medication.model.MedicationRegistrationRequest;

import java.util.Set;

public interface DeliveryService {

    //
    boolean loadDroneWithMedicationItem(String droneSerialNumber, Set<MedicationRegistrationRequest> medications);

    GetLoadedMedicationResult retrieveLoadedMedicationForAGivenDrone(String droneSerialNumber, int pageNumber);
}

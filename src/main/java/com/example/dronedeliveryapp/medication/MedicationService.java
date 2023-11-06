package com.example.dronedeliveryapp.medication;

import com.example.dronedeliveryapp.medication.model.Medication;
import com.example.dronedeliveryapp.medication.model.MedicationRegistrationRequest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

public interface MedicationService {

    static double getMedicationWeight(Set<MedicationRegistrationRequest> medications) {
        Assert.notNull(medications, "Medications cannot be null");
        //We want to check if the max wight of the drone can accommodate the delivery size.
        double accumulator = 0d;

        for (MedicationRegistrationRequest medication : medications) {
            Double weight = medication.getWeightInGrams();
            if (weight == null) throw new IllegalStateException("Medication weight cannot be null");
            accumulator = accumulator + weight;
        }

        return accumulator;
    }

    List<Medication> addMedications(Set<MedicationRegistrationRequest> medicationList);
}

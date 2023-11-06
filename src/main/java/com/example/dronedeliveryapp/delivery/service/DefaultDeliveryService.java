package com.example.dronedeliveryapp.delivery.service;

import com.example.dronedeliveryapp.Weight;
import com.example.dronedeliveryapp.delivery.DeliveryServiceException;
import com.example.dronedeliveryapp.delivery.model.Delivery;
import com.example.dronedeliveryapp.delivery.model.DeliveryDTO;
import com.example.dronedeliveryapp.delivery.model.GetLoadedMedicationResult;
import com.example.dronedeliveryapp.delivery.repository.DeliveryRepository;
import com.example.dronedeliveryapp.drone.model.Drone;
import com.example.dronedeliveryapp.drone.model.PagingInfo;
import com.example.dronedeliveryapp.drone.model.Percentage;
import com.example.dronedeliveryapp.drone.model.State;
import com.example.dronedeliveryapp.drone.service.DroneService;
import com.example.dronedeliveryapp.medication.model.Medication;
import com.example.dronedeliveryapp.medication.model.MedicationRegistrationRequest;
import com.example.dronedeliveryapp.medication.MedicationService;
import com.example.dronedeliveryapp.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DefaultDeliveryService implements DeliveryService {

    private final DroneService droneService;
    private final MedicationService medicationService;
    private final DeliveryRepository deliveryRepository;


    public DefaultDeliveryService(DroneService droneService, MedicationService medicationService, DeliveryRepository deliveryRepository) {
        this.droneService = droneService;
        this.medicationService = medicationService;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public boolean loadDroneWithMedicationItem(String droneSerialNumber, Set<MedicationRegistrationRequest> medications) {

        //Check if the medications is empty
        if (medications == null || medications.isEmpty()) {
            throw new DeliveryServiceException("Medications cannot be null/empty", HttpStatus.BAD_REQUEST);
        }

        Drone drone = droneService.retrieveBySerialNumber(droneSerialNumber);

        //Check if the drone is idle.
        //If not fail.
        if (State.IDLE != drone.getState()) {
            throw new DeliveryServiceException("Drone is not in IDLE state", HttpStatus.BAD_REQUEST);
        }

        double accumulator = MedicationService.getMedicationWeight(medications);

        Weight weight = drone.getWeight();
        if (weight == null) throw new IllegalStateException("Drone weight cannot be null");

        if (weight.value() < accumulator) {
            throw new DeliveryServiceException("Drone is being overloaded", HttpStatus.BAD_REQUEST);
        }

        //Check if the battery is greater than 25.
        //if false fail.
        Percentage batteryCapacity = drone.getBatteryCapacity();
        if (batteryCapacity == null) throw new IllegalStateException("Drone weight cannot be null");

        if (batteryCapacity.value() < 25) {
            throw new DeliveryServiceException("Drone battery below 25 percent", HttpStatus.BAD_REQUEST);
        }

        //Set drone to loading.
        drone.setState(State.LOADING);
        Set<Medication> medicationSet = new HashSet<>(medicationService.addMedications(medications));

        //Register relationship.
        saveDelivery(medicationSet, drone);
        //Save delivery

        //Set drone to loaded.
        drone.setState(State.LOADED);
        return false;
    }

    private void saveDelivery(Set<Medication> medications, Drone drone) {

        Assert.notEmpty(medications, "Medications cannot be empty");
        String sessionId = SecurityUtil.sessionId();

        for (Medication medication : medications) {
            Delivery delivery = new Delivery(sessionId, drone.getDroneId(), medication.getMedicationId());
            deliveryRepository.save(delivery);
        }
    }


    @Override
    public GetLoadedMedicationResult retrieveLoadedMedicationForAGivenDrone(String droneSerialNumber, int pageNumber) {

        PagingInfo pagingInfo = new PagingInfo(pageNumber);
        List<DeliveryDTO> deliveryDTO = deliveryRepository.retrieveLoadedMedicationForAGivenDrone(droneSerialNumber, pagingInfo.getCurrentPage() - 1, pagingInfo.getPageSize());

        if (CollectionUtils.isEmpty(deliveryDTO)) {
            throw new DeliveryServiceException("No items found", HttpStatus.NOT_FOUND);
        }

        PagingInfo newPagingInfo = new PagingInfo(pagingInfo.getPageSize(), pagingInfo.getCurrentPage(), deliveryDTO.get(0).getTotalCount());

        return new GetLoadedMedicationResult(deliveryDTO, newPagingInfo);
    }
}

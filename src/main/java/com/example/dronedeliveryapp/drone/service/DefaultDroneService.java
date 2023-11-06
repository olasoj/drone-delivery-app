package com.example.dronedeliveryapp.drone.service;

import com.example.dronedeliveryapp.Weight;
import com.example.dronedeliveryapp.drone.DroneServiceException;
import com.example.dronedeliveryapp.drone.model.*;
import com.example.dronedeliveryapp.drone.repository.DroneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultDroneService implements DroneService {

    private final DroneRepository droneRepository;

    public DefaultDroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    @Transactional(transactionManager = "txManager")
    public DroneRegistrationResult registerDrone(DroneRegistrationRequest droneRegistrationRequest) {

        Assert.notNull(droneRegistrationRequest, "Drone cannot be null");

        String serialNumber = droneRegistrationRequest.getSerialNumber();

        if ((serialNumber == null) || serialNumber.isBlank() || serialNumber.length() > 100) {
            throw new DroneServiceException("Invalid serial number: the serial number must be fewer than 100 characters long.", HttpStatus.BAD_REQUEST);
        }

        Optional<Drone> existingDrone = droneRepository.retrieveBySerialNumber(serialNumber);
        if (existingDrone.isPresent()) throw new DroneServiceException("Drone already registered", HttpStatus.CONFLICT);

        Double weight = droneRegistrationRequest.getWeightInGrams();

        if (weight == null || weight <= 0 || weight > 500) {
            throw new DroneServiceException("Invalid weight: the weight must be between 0 and  500.", HttpStatus.BAD_REQUEST);
        }

        Drone drone = new Drone(
                droneRegistrationRequest.getSerialNumber().strip(),
                droneRegistrationRequest.getModel(),
                new Weight("grams", weight),
                State.IDLE,
                new Percentage(100)
        );

        droneRepository.save(drone);
        return new DroneRegistrationResult(drone.getSerialNumber());
    }

    @Override
    public Drone retrieveBySerialNumber(String serialNumber) {
        return droneRepository.retrieveBySerialNumber(serialNumber)
                .orElseThrow(() -> new DroneServiceException("No drone found", HttpStatus.NOT_FOUND));
    }

    @Override
    public DroneDTOWithPageResult retrieveIdleDrones(int pageNumber) {

        PagingInfo pagingInfo = new PagingInfo(pageNumber);
        List<DroneDTO> droneDTOS = droneRepository.retrieveAllIdleDrone(pagingInfo.getCurrentPage() - 1, pagingInfo.getPageSize());

        int totalCount = (droneDTOS == null || droneDTOS.isEmpty()) ? 0 : droneDTOS.get(0).getTotalCount();
        PagingInfo newPagingInfo = new PagingInfo(pagingInfo.getPageSize(), pagingInfo.getCurrentPage(), totalCount);

        return new DroneDTOWithPageResult(droneDTOS, newPagingInfo);
    }

    @Override
    public DroneWithPageResult retrieveAllDrones(int pageNumber) {

        PagingInfo pagingInfo = new PagingInfo(pageNumber);
        List<Drone> drones = droneRepository.retrieveAllDrone(pagingInfo.getCurrentPage(), pagingInfo.getPageSize());

        PagingInfo newPagingInfo = new PagingInfo(pagingInfo.getPageSize(), pagingInfo.getCurrentPage(), pagingInfo.getPageSize());

        return new DroneWithPageResult(drones, newPagingInfo);
    }

    @Override
    public Percentage retrieveDroneBatteryLevel(String serialNumber) {

        if (serialNumber == null || serialNumber.isBlank()) throw new DroneServiceException("Serial number not provides", HttpStatus.BAD_REQUEST);

        BigDecimal batteryCapacity = droneRepository.retrieveBatteryLevelDTOBySerialNumber(serialNumber);
        if (batteryCapacity == null) throw new DroneServiceException("Drone not found", HttpStatus.NOT_FOUND);

        return new Percentage(batteryCapacity.doubleValue());
    }
}


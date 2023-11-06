package com.example.dronedeliveryapp.drone.service;


import com.example.dronedeliveryapp.drone.model.*;

public interface DroneService {

    DroneRegistrationResult registerDrone(DroneRegistrationRequest droneRegistrationRequest);
    Drone retrieveBySerialNumber(String serialNumber);
    DroneDTOWithPageResult retrieveIdleDrones(int pageNumber);
    DroneWithPageResult retrieveAllDrones(int pageNumber);
    Percentage retrieveDroneBatteryLevel(String serialNumber);
}


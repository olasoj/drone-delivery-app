package com.example.dronedeliveryapp.drone.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DroneModel {
    LIGHTWEIGHT("Lightweight"),
    MIDDLEWEIGHT("Middleweight"),
    CRUISERWEIGHT("Cruiserweight"),
    HEAVYWEIGHT("Heavyweight");


    private static final Map<String, DroneModel> droneModelMap;

    static {
        droneModelMap = new HashMap<>();
        for (DroneModel droneModel : DroneModel.values())
            droneModelMap.put(droneModel.getName(), droneModel);
    }

    final String name;

    DroneModel(String name) {
        this.name = name;
    }

    public static DroneModel assemble(String name) {
        return name == null ? null : droneModelMap.get(name);
    }

    @JsonValue
    public String getName() {
        return name;
    }

}

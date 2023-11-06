package com.example.dronedeliveryapp.drone.model;

import org.springframework.util.Assert;

import java.util.List;

public record DroneWithPageResult(List<Drone> drones, PagingInfo pagingInfo) {

    public DroneWithPageResult {
        Assert.notNull(drones, "DroneList cannot be null");
        Assert.notNull(pagingInfo, "PagingInfo cannot be null");
    }
}

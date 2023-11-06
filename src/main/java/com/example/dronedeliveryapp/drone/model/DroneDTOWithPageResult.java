package com.example.dronedeliveryapp.drone.model;

import org.springframework.util.Assert;

import java.util.List;

public record DroneDTOWithPageResult(List<DroneDTO> droneDTOS, PagingInfo pagingInfo) {

    public DroneDTOWithPageResult {
        Assert.notNull(droneDTOS, "DroneDTO cannot be null");
        Assert.notNull(pagingInfo, "PagingInfo cannot be null");
    }
}

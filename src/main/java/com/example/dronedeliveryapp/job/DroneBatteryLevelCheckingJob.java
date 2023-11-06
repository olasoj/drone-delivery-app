package com.example.dronedeliveryapp.job;

import com.example.dronedeliveryapp.drone.model.Drone;
import com.example.dronedeliveryapp.drone.service.DroneService;
import com.example.dronedeliveryapp.drone.model.DroneWithPageResult;
import com.example.dronedeliveryapp.drone.model.Percentage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class DroneBatteryLevelCheckingJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroneBatteryLevelCheckingJob.class);

    private final TaskScheduler batteryCheckerTaskScheduler;
    private final DroneService droneService;

    public DroneBatteryLevelCheckingJob(@Qualifier("batteryCheckerTaskScheduler") TaskScheduler batteryCheckerTaskScheduler, DroneService droneService) {
        this.batteryCheckerTaskScheduler = batteryCheckerTaskScheduler;
        this.droneService = droneService;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        Instant now = Instant.now();

        reDronesBatteryLevel();

        Runnable initializeOverdraftGraceDates = () -> reDronesBatteryLevel();

        Duration backOffDuration = Duration.ofHours(1);
        Instant nextProcessingTime = now.plus(backOffDuration);
        batteryCheckerTaskScheduler.scheduleAtFixedRate(initializeOverdraftGraceDates, nextProcessingTime, backOffDuration);
    }

    private void reDronesBatteryLevel() {

        DroneWithPageResult droneWithPageResult = droneService.retrieveAllDrones(1);

        Assert.notNull(droneWithPageResult, "DroneWithPageResult cannot be null");

        List<Drone> drones = droneWithPageResult.drones();
        LOGGER.debug("Began querying {} drones:", drones.size());

        for (Drone drone : drones) {

            Assert.notNull(drone, "Drone cannot be null");

            Percentage batteryCapacity = drone.getBatteryCapacity();
            Assert.notNull(batteryCapacity, "BatteryCapacity cannot be null");

            LOGGER.info(
                    "Auditing drone with serial number: {} & battery capacity: {} ",
                    drone.getSerialNumber(),
                    batteryCapacity.value()
            );

        }

    }
}

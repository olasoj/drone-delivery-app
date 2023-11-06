package com.example.dronedeliveryapp.drone;

import com.example.dronedeliveryapp.drone.model.DroneDTOWithPageResult;
import com.example.dronedeliveryapp.drone.model.DroneRegistrationRequest;
import com.example.dronedeliveryapp.drone.model.DroneRegistrationResult;
import com.example.dronedeliveryapp.drone.model.Percentage;
import com.example.dronedeliveryapp.drone.service.DroneService;
import com.example.dronedeliveryapp.util.response.model.Response;
import com.example.dronedeliveryapp.util.response.transformer.ResponseAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping(value = "/drone", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DroneInboundRestController {

    private final DroneService droneService;

    public DroneInboundRestController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<Response<DroneRegistrationResult>> registerDrone(@Valid @RequestBody DroneRegistrationRequest registrationRequest) throws URISyntaxException {
        DroneRegistrationResult registrationResult = droneService.registerDrone(registrationRequest);
        Response<DroneRegistrationResult> model = ResponseAssembler.toResponse(HttpStatus.OK, registrationResult);
        return ResponseEntity.created(new URI("")).body(model);
    }

    @GetMapping(path = "/idle")
    public ResponseEntity<Response<DroneDTOWithPageResult>> getIdleDrone(@RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        DroneDTOWithPageResult droneDTOWithPageResult = droneService.retrieveIdleDrones(pageNumber);
        Response<DroneDTOWithPageResult> model = ResponseAssembler.toResponse(HttpStatus.OK, droneDTOWithPageResult);
        return ResponseEntity.ok().body(model);
    }

    @GetMapping(path = "/battery-capacity/{droneSerialNumber}")
    public ResponseEntity<Response<Map<String, Double>>> getDroneBatteryCapacity(@PathVariable("droneSerialNumber") String droneSerialNumber) {
        Percentage percentage = droneService.retrieveDroneBatteryLevel(droneSerialNumber);
        Response<Map<String, Double>> model = ResponseAssembler.toResponse(HttpStatus.OK, Map.of("capacity", percentage.value()));
        return ResponseEntity.ok().body(model);
    }


}

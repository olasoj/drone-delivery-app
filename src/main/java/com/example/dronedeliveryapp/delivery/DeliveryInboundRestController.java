package com.example.dronedeliveryapp.delivery;

import com.example.dronedeliveryapp.delivery.model.DeliveryRequest;
import com.example.dronedeliveryapp.delivery.model.GetLoadedMedicationResult;
import com.example.dronedeliveryapp.delivery.service.DeliveryService;
import com.example.dronedeliveryapp.util.response.model.Response;
import com.example.dronedeliveryapp.util.response.model.ResponseStatus;
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
@RequestMapping(value = "/delivery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryInboundRestController {

    private final DeliveryService deliveryService;

    public DeliveryInboundRestController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping(path = "/{droneSerialNumber}")
    public ResponseEntity<Response<Map<String, ResponseStatus>>> loadDrone(@PathVariable("droneSerialNumber") String droneSerialNumber, @Valid @RequestBody DeliveryRequest deliveryRequest) throws URISyntaxException {
        deliveryService.loadDroneWithMedicationItem(droneSerialNumber, deliveryRequest.medicationRegistrationRequests());
        Response<Map<String, ResponseStatus>> model = ResponseAssembler.toResponse(HttpStatus.CREATED, Map.of("message", ResponseStatus.SUCCESS));
        return ResponseEntity
                .created(new URI(""))
                .body(model);
    }

    @GetMapping(path = "/{droneSerialNumber}")
    public ResponseEntity<Response<GetLoadedMedicationResult>> getLoadedDrone(@PathVariable("droneSerialNumber") String droneSerialNumber, @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        GetLoadedMedicationResult getLoadedMedicationResult = deliveryService.retrieveLoadedMedicationForAGivenDrone(droneSerialNumber, pageNumber);
        Response<GetLoadedMedicationResult> model = ResponseAssembler.toResponse(HttpStatus.OK, getLoadedMedicationResult);
        return ResponseEntity
                .ok()
                .body(model);
    }

}

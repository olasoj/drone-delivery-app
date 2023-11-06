package com.example.dronedeliveryapp.delivery.model;

import com.example.dronedeliveryapp.drone.model.PagingInfo;
import com.example.dronedeliveryapp.drone.model.State;
import com.example.dronedeliveryapp.medication.model.MedicationDTO;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class GetLoadedMedicationResult {

    private final String trackingId;
    private final String serialNumber;
    private final State droneState;
    private final Double batteryCapacity;
    private final PagingInfo medicationPagingInfo;

    private final List<MedicationDTO> medicationDTO;

    private GetLoadedMedicationResult() {
        this.trackingId = "2";
        this.serialNumber = "SQEQ1";
        this.droneState = State.LOADED;
        this.batteryCapacity = 100d;
        this.medicationPagingInfo = new PagingInfo();
        this.medicationDTO = Collections.emptyList();
    }

    public GetLoadedMedicationResult(List<DeliveryDTO> deliveryDTOList, PagingInfo medicationPagingInfo) {
        this.medicationPagingInfo = medicationPagingInfo;

        Assert.notEmpty(deliveryDTOList, "DeliveryDTO cannot be empty");
        DeliveryDTO deliveryDTO = deliveryDTOList.get(0);

        this.trackingId = deliveryDTO.getTrackingId();
        this.serialNumber = deliveryDTO.getSerialNumber();
        this.droneState = deliveryDTO.getDroneState();
        this.batteryCapacity = deliveryDTO.getBatteryCapacity();

        this.medicationDTO = deliveryDTOList
                .stream()
                .map(deliveryDTO1 -> new MedicationDTO(
                        deliveryDTO1.getImage(),
                        deliveryDTO1.getMedicationName(),
                        deliveryDTO1.getCode(),
                        deliveryDTO1.getWeightLimit(),
                        deliveryDTO1.getWeightUnit()
                ))
                .collect(Collectors.toCollection(() -> new ArrayList<>(deliveryDTOList.size())));
    }

    public static GetLoadedMedicationResult testGetLoadedMedicationResult() {
        return new GetLoadedMedicationResult();
    }

    public String getTrackingId() {
        return trackingId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public List<MedicationDTO> getMedicationDTO() {
        return medicationDTO;
    }

    public State getDroneState() {
        return droneState;
    }

    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public PagingInfo getMedicationPagingInfo() {
        return medicationPagingInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GetLoadedMedicationResult that)) return false;
        return Objects.equals(trackingId, that.trackingId) && Objects.equals(serialNumber, that.serialNumber) && Objects.equals(medicationDTO, that.medicationDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingId, serialNumber, medicationDTO);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GetLoadedMedicationResult.class.getSimpleName() + "[", "]")
                .add("trackingId='" + trackingId + "'")
                .add("serialNumber='" + serialNumber + "'")
                .add("droneState=" + droneState)
                .add("batteryCapacity=" + batteryCapacity)
                .add("pagingInfo=" + medicationPagingInfo)
                .add("medicationDTO=" + medicationDTO)
                .toString();
    }
}

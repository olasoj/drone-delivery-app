package com.example.dronedeliveryapp.delivery.model;

import com.example.dronedeliveryapp.config.persistence.audit.AuditedEntity;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "delivery")
public class Delivery extends AuditedEntity {

    @Id
    @Column(name = "delivery_id", updatable = false, nullable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_Sequence")
    @SequenceGenerator(name = "delivery_Sequence", sequenceName = "delivery_delivery_id_seq", allocationSize = 1)
    private Long deliveryId;

    @Column(name = "tracking_id", nullable = false)
    private String trackingId;

    @Column(name = "drone_id", nullable = false)
    private Long drone;

    @Column(name = "medication_id", nullable = false)
    private Long medications;

    @Version()
    private Integer version;

    public Delivery() {
    }

    public Delivery(String trackingId, Long drone, Long medications) {
        this.trackingId = trackingId;
        this.drone = drone;
        this.medications = medications;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getVersion() {
        return version;
    }

    public Long getDrone() {
        return drone;
    }

    public void setDrone(Long drone) {
        this.drone = drone;
    }

    public Long getMedications() {
        return medications;
    }

    public void setMedications(Long medications) {
        this.medications = medications;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Delivery delivery)) return false;
        return Objects.equals(deliveryId, delivery.deliveryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Delivery.class.getSimpleName() + "[", "]")
                .add("deliveryId=" + deliveryId)
                .add("createdAt=" + super.getCreatedAt())
                .add("updatedAt=" + super.getUpdatedAt())
                .add("createdBy='" + super.getCreatedBy() + "'")
                .add("updatedBy='" + super.getUpdatedBy() + "'")
                .add("version=" + version)
                .toString();
    }

}

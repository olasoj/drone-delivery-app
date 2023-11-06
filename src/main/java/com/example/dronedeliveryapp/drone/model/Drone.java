package com.example.dronedeliveryapp.drone.model;

import com.example.dronedeliveryapp.Weight;
import com.example.dronedeliveryapp.config.persistence.audit.AuditedEntity;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.Type;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "drone")
public class Drone extends AuditedEntity {

    @Id
    @Column(name = "drone_id", updatable = false, nullable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drone_Sequence")
    @SequenceGenerator(name = "drone_Sequence", sequenceName = "drone_drone_id_seq", allocationSize = 1)
    private Long droneId;

    @Column(name = "serial_number", nullable = false, unique = true)
    @Min(1L)
    @Max(100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model", columnDefinition = "dronemodel", nullable = false)
//    @Convert(converter = DroneModelConverter.class)
    @Type(PostgreSQLEnumType.class)
    private DroneModel model;

    @Embedded
    @AttributeOverride(name = "unit", column = @Column(name = "weight_unit", nullable = false))
    @AttributeOverride(name = "value", column = @Column(name = "weight_limit", nullable = false))
    private Weight weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_state", columnDefinition = "dronestate", nullable = false)
    @Type(PostgreSQLEnumType.class)
    private State state;//(IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING)

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "battery_capacity", nullable = false))
    private Percentage batteryCapacity;//(percentage);.

    @Version()
    private Integer version;

    public Drone() {
    }

    public Drone(String serialNumber, DroneModel model, Weight weight, State state, Percentage batteryCapacity) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weight = weight;
        this.state = state;
        this.batteryCapacity = batteryCapacity;
    }

    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Percentage getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Percentage batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Drone drone)) return false;
        return Objects.equals(serialNumber, drone.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Drone.class.getSimpleName() + "[", "]")
                .add("droneId=" + droneId)
                .add("createdAt=" + super.getCreatedAt())
                .add("updatedAt=" + super.getUpdatedAt())
                .add("createdBy='" + super.getCreatedBy() + "'")
                .add("updatedBy='" + super.getUpdatedBy() + "'")
                .add("serialNumber='" + serialNumber + "'")
                .add("model=" + model)
                .add("weight=" + weight)
                .add("state=" + state)
                .add("batteryCapacity=" + batteryCapacity)
                .add("version=" + version)
                .toString();
    }

}


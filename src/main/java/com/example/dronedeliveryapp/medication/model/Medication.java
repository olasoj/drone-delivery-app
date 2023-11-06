package com.example.dronedeliveryapp.medication.model;

import com.example.dronedeliveryapp.Weight;
import com.example.dronedeliveryapp.config.persistence.audit.AuditedEntity;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "medication")
public class Medication extends AuditedEntity {

    @Id
    @Column(name = "medication_id", updatable = false, nullable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_Sequence")
    @SequenceGenerator(name = "medication_Sequence", sequenceName = "medication_medication_id_seq", allocationSize = 1)
    private Long medicationId;

    @Column(name = "medication_name", nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(name = "unit", column = @Column(name = "weight_unit", nullable = false))
    @AttributeOverride(name = "value", column = @Column(name = "weight_limit", nullable = false))
    private Weight weight;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "image", nullable = false)
    private String image;// (picture of the medication case)'

    @Version()
    private Integer version;

    public Medication() {
    }

    public Medication(String name, Weight weight, String code, String image) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Medication that)) return false;
        return Objects.equals(medicationId, that.medicationId) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicationId, code);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Medication.class.getSimpleName() + "[", "]")
                .add("medicationId=" + medicationId)
                .add("createdAt=" + super.getCreatedAt())
                .add("updatedAt=" + super.getUpdatedAt())
                .add("createdBy='" + super.getCreatedBy() + "'")
                .add("updatedBy='" + super.getUpdatedBy() + "'")
                .add("name='" + name + "'")
                .add("weight=" + weight)
                .add("code='" + code + "'")
                .add("image='" + image + "'")
                .add("version=" + version)
                .toString();
    }

}

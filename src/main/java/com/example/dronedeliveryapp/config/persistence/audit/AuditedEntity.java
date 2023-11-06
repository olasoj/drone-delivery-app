package com.example.dronedeliveryapp.config.persistence.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AuditedEntity {

    @Column(name = "created_at", insertable=false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable=false)
    private Instant updatedAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    @LastModifiedBy
    private String updatedBy;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AuditedEntity that)) return false;
        return Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, updatedAt, createdBy, updatedBy);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuditedEntity.class.getSimpleName() + "[", "]")
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .add("createdBy='" + createdBy + "'")
                .add("updatedBy='" + updatedBy + "'")
                .toString();
    }

    @PrePersist
    protected void onCreate() {
        updatedBy = "SYSTEM";
    }

}

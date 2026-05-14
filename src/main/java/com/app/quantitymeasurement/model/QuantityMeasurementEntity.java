package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_entity", indexes = {
        @Index(name="idx_operation", columnList="operation"),
        @Index(name="idx_this_measurement_type", columnList="this_measurement_type"),
        @Index(name="idx_created_at", columnList="created_at")
})
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {
	public QuantityMeasurementEntity() {
		
	}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "this_value", nullable = false)
    public double thisValue;
    @Column(name = "this_unit", nullable = false)
    public String thisUnit;
    @Column(name = "this_measurement_type", nullable = false)
    public String thisMeasurementType;
    @Column(name = "that_value", nullable = false)
    public double thatValue;
    @Column(name = "that_unit", nullable = false)
    public String thatUnit;
    @Column(name = "that_measurement_type", nullable = false)
    public String thatMeasurementType;
    // eg. COMPARE, "CONVERT", "ADD", "SUBTRACT"
    @Column(name = "operation", nullable = false)
    public String operation;
    @Column(name = "result_value")
    public double resultValue;
    @Column(name = "result_unit")
    public String resultUnit;
    @Column(name = "result_measurement_type")
    public String resultMeasurementType;

    // for comparison results like "Equal" or "Not Equal"
    @Column(name = "result_string")
    public String resultString;

    //flag to indicate if error occurred during operation
    @Column(name = "is_error")
    public boolean isError;

    // For capturing any error messages during operation
    @Column(name = "error_message")
    public String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

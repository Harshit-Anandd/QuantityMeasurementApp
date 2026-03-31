package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "quantity_measurement_entity",
       indexes = {
           @Index(name = "idx_operation", columnList = "operation"),
           @Index(name = "idx_measurement_type", columnList = "this_measurement_type"),
           @Index(name = "idx_created_at", columnList = "created_at")
       })
@Data
public class QuantityMeasurementEntity {

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

    @Column(name = "operation", nullable = false)
    public String operation;

    @Column(name = "result_value")
    public double resultValue;

    @Column(name = "result_unit")
    public String resultUnit;

    @Column(name = "result_measurement_type")
    public String resultMeasurementType;

    @Column(name = "result_string")
    public String resultString;

    @Column(name = "is_error")
    public boolean isError;

    @Column(name = "error_message")
    public String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public QuantityMeasurementEntity() {
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public QuantityMeasurementEntity(QuantityDTO thisQuantity, QuantityDTO thatQuantity, String operation, String result) {
        populateOperands(thisQuantity, thatQuantity, operation);
        this.resultString = result;
    }

    public QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String operation,
            double resultValue,
            String resultUnit,
            String resultMeasurementType) {
        populateOperands(thisQuantity, thatQuantity, operation);
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;
    }

    public QuantityMeasurementEntity(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String operation,
            String errorMessage,
            boolean isError) {
        populateOperands(thisQuantity, thatQuantity, operation);
        this.errorMessage = errorMessage;
        this.isError = isError;
    }

    private void populateOperands(QuantityDTO thisQuantity, QuantityDTO thatQuantity, String operation) {
        this.thisValue = thisQuantity == null || thisQuantity.value == null ? 0 : thisQuantity.value;
        this.thisUnit = thisQuantity == null ? null : thisQuantity.unit;
        this.thisMeasurementType = thisQuantity == null ? null : thisQuantity.measurementType;

        this.thatValue = thatQuantity == null || thatQuantity.value == null ? 0 : thatQuantity.value;
        this.thatUnit = thatQuantity == null ? null : thatQuantity.unit;
        this.thatMeasurementType = thatQuantity == null ? null : thatQuantity.measurementType;

        this.operation = operation;
    }
}

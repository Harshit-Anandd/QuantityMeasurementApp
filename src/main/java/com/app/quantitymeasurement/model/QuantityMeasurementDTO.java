package com.app.quantitymeasurement.model;

import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

@Data
public class QuantityMeasurementDTO {

    public double thisValue;
    public String thisUnit;
    public String thisMeasurementType;

    public double thatValue;
    public String thatUnit;
    public String thatMeasurementType;

    public String operation;

    public String resultString;
    public double resultValue;
    public String resultUnit;
    public String resultMeasurementType;

    @JsonProperty("error")
    public boolean error;

    public String errorMessage;

    // 🔁 ENTITY → DTO
    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

        dto.thisValue = entity.thisValue;
        dto.thisUnit = entity.thisUnit;
        dto.thisMeasurementType = entity.thisMeasurementType;

        dto.thatValue = entity.thatValue;
        dto.thatUnit = entity.thatUnit;
        dto.thatMeasurementType = entity.thatMeasurementType;

        dto.operation = entity.operation;

        dto.resultString = entity.resultString;
        dto.resultValue = entity.resultValue;
        dto.resultUnit = entity.resultUnit;
        dto.resultMeasurementType = entity.resultMeasurementType;

        dto.error = entity.isError;
        dto.errorMessage = entity.errorMessage;

        return dto;
    }

    // 🔁 DTO → ENTITY
    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.thisValue = this.thisValue;
        entity.thisUnit = this.thisUnit;
        entity.thisMeasurementType = this.thisMeasurementType;

        entity.thatValue = this.thatValue;
        entity.thatUnit = this.thatUnit;
        entity.thatMeasurementType = this.thatMeasurementType;

        entity.operation = this.operation;

        entity.resultString = this.resultString;
        entity.resultValue = this.resultValue;
        entity.resultUnit = this.resultUnit;
        entity.resultMeasurementType = this.resultMeasurementType;

        entity.isError = this.error;
        entity.errorMessage = this.errorMessage;

        return entity;
    }

    // 🔁 ENTITY LIST → DTO LIST
    public static List<QuantityMeasurementDTO> fromList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    // 🔁 DTO LIST → ENTITY LIST
    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        return dtos.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .toList();
    }
}
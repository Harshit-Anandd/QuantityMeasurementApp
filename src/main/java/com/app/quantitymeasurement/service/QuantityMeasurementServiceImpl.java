package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.IMeasurable;
import com.app.quantitymeasurement.model.LengthUnit;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.Quantity;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.TemperatureUnit;
import com.app.quantitymeasurement.model.VolumeUnit;
import com.app.quantitymeasurement.model.WeightUnit;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());
    private static final String DEFAULT_VALUE = "N/A";
    private static final double EPSILON = 0.0001d;

    @Autowired
    private QuantityMeasurementRepository repository;

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO thisQ, QuantityDTO thatQ) {
        try {
            logger.info("Service operation started: compare");
            validateSameMeasurementType(thisQ, thatQ, "compare");

            Quantity<IMeasurable> thisQuantityModel = convertDtoToModel(thisQ);
            Quantity<IMeasurable> thatQuantityModel = convertDtoToModel(thatQ);

            boolean result = Math.abs(thisQuantityModel.toBase() - thatQuantityModel.toBase()) < EPSILON;

            QuantityMeasurementEntity entity = createBaseEntity(thisQ, thatQ, OperationType.COMPARE);
            entity.resultString = Boolean.toString(result);

            logger.info("Service operation completed: compare");
            return saveAndReturn(entity);

        } catch (QuantityMeasurementException e) {
            logger.log(Level.SEVERE, "Service operation failed: compare", e);
            saveError(OperationType.COMPARE, thisQ, thatQ, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Service operation failed: compare", e);
            saveError(OperationType.COMPARE, thisQ, thatQ, e.getMessage());
            throw e;
        }
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO thisQ, QuantityDTO thatQ) {
        try {
            logger.info("Service operation started: convert");
            validateSameMeasurementType(thisQ, thatQ, "convert");

            Quantity<IMeasurable> thisQuantityModel = convertDtoToModel(thisQ);
            Quantity<IMeasurable> targetQuantityModel = convertDtoToModel(thatQ);

            double convertedValue = targetQuantityModel.getUnit().convertFromBaseUnit(thisQuantityModel.toBase());

            QuantityMeasurementEntity entity = createBaseEntity(thisQ, thatQ, OperationType.CONVERT);
            entity.resultValue = convertedValue;
            entity.resultUnit = thatQ.unit;
            entity.resultMeasurementType = thatQ.measurementType;

            logger.info("Service operation completed: convert");
            return saveAndReturn(entity);

        } catch (QuantityMeasurementException e) {
            logger.log(Level.SEVERE, "Service operation failed: convert", e);
            saveError(OperationType.CONVERT, thisQ, thatQ, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Service operation failed: convert", e);
            saveError(OperationType.CONVERT, thisQ, thatQ, e.getMessage());
            throw e;
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ) {
        return this.add(thisQ, thatQ, thisQ);
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ, QuantityDTO targetUnitDTO) {
        try {
            logger.info("Service operation started: add-with-target-unit");
            validateArithmetic(thisQ, thatQ, "add");
            validateTargetUnit(thisQ, targetUnitDTO, "add-with-target-unit");

            Quantity<IMeasurable> thisQuantityModel = convertDtoToModel(thisQ);
            Quantity<IMeasurable> thatQuantityModel = convertDtoToModel(thatQ);
            Quantity<IMeasurable> targetQuantityModel = convertDtoToModel(targetUnitDTO);

            double resultBaseValue = thisQuantityModel.toBase() + thatQuantityModel.toBase();
            double result = targetQuantityModel.getUnit().convertFromBaseUnit(resultBaseValue);

            QuantityMeasurementEntity entity = createBaseEntity(thisQ, thatQ, OperationType.ADD);
            entity.resultValue = result;
            entity.resultUnit = targetUnitDTO.unit;
            entity.resultMeasurementType = targetUnitDTO.measurementType;

            logger.info("Service operation completed: add-with-target-unit");
            return saveAndReturn(entity);

        } catch (QuantityMeasurementException e) {
            logger.log(Level.SEVERE, "Service operation failed: add-with-target-unit", e);
            saveError(OperationType.ADD, thisQ, thatQ, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Service operation failed: add-with-target-unit", e);
            saveError(OperationType.ADD, thisQ, thatQ, e.getMessage());
            throw e;
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ) {
        return this.subtract(thisQ, thatQ, thisQ);
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ, QuantityDTO targetUnitDTO) {
        try {
            logger.info("Service operation started: subtract-with-target-unit");
            validateArithmetic(thisQ, thatQ, "subtract");
            validateTargetUnit(thisQ, targetUnitDTO, "subtract-with-target-unit");

            Quantity<IMeasurable> thisQuantityModel = convertDtoToModel(thisQ);
            Quantity<IMeasurable> thatQuantityModel = convertDtoToModel(thatQ);
            Quantity<IMeasurable> targetQuantityModel = convertDtoToModel(targetUnitDTO);

            double resultBaseValue = thisQuantityModel.toBase() - thatQuantityModel.toBase();
            double result = targetQuantityModel.getUnit().convertFromBaseUnit(resultBaseValue);

            QuantityMeasurementEntity entity = createBaseEntity(thisQ, thatQ, OperationType.SUBTRACT);
            entity.resultValue = result;
            entity.resultUnit = targetUnitDTO.unit;
            entity.resultMeasurementType = targetUnitDTO.measurementType;

            logger.info("Service operation completed: subtract-with-target-unit");
            return saveAndReturn(entity);

        } catch (QuantityMeasurementException e) {
            logger.log(Level.SEVERE, "Service operation failed: subtract-with-target-unit", e);
            saveError(OperationType.SUBTRACT, thisQ, thatQ, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Service operation failed: subtract-with-target-unit", e);
            saveError(OperationType.SUBTRACT, thisQ, thatQ, e.getMessage());
            throw e;
        }
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO thisQ, QuantityDTO thatQ) {
        try {
            logger.info("Service operation started: divide");
            validateArithmetic(thisQ, thatQ, "divide");

            Quantity<IMeasurable> thisQuantityModel = convertDtoToModel(thisQ);
            Quantity<IMeasurable> thatQuantityModel = convertDtoToModel(thatQ);

            double divisor = thatQuantityModel.toBase();
            if (Math.abs(divisor) < EPSILON) {
                throw new ArithmeticException("Divide by zero");
            }

            double result = thisQuantityModel.toBase() / divisor;

            QuantityMeasurementEntity entity = createBaseEntity(thisQ, thatQ, OperationType.DIVIDE);
            entity.resultValue = result;

            logger.info("Service operation completed: divide");
            return saveAndReturn(entity);

        } catch (QuantityMeasurementException e) {
            logger.log(Level.SEVERE, "Service operation failed: divide", e);
            saveError(OperationType.DIVIDE, thisQ, thatQ, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Service operation failed: divide", e);
            saveError(OperationType.DIVIDE, thisQ, thatQ, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        logger.info(String.format("Service operation started: getOperationHistory for %s", operation));
        String normalizedOperation = normalizeOperation(operation);
        return QuantityMeasurementDTO.fromEntityList(repository.findByOperation(normalizedOperation));
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
        logger.info(String.format("Service operation started: getMeasurementsByType for %s", type));
        return QuantityMeasurementDTO.fromEntityList(repository.findByThisMeasurementType(type));
    }

    @Override
    public long getOperationCount(String operation) {
        logger.info(String.format("Service operation started: getOperationCount for %s", operation));
        String normalizedOperation = normalizeOperation(operation);
        return repository.countByOperationAndIsErrorFalse(normalizedOperation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        logger.info("Service operation started: getErrorHistory");
        return QuantityMeasurementDTO.fromEntityList(repository.findByIsErrorTrue());
    }

    private Quantity<IMeasurable> convertDtoToModel(QuantityDTO dto) {
        if (dto == null || dto.value == null) {
            throw new QuantityMeasurementException("Quantity input cannot be null");
        }

        String measurementType = dto.measurementType;
        String unit = dto.unit;

        if (measurementType == null || unit == null) {
            throw new QuantityMeasurementException("Measurement type and unit are required");
        }

        IMeasurable measurableUnit;
        try {
            measurableUnit = switch (measurementType) {
                case "LengthUnit" -> LengthUnit.valueOf(unit);
                case "VolumeUnit" -> VolumeUnit.valueOf(unit);
                case "WeightUnit" -> WeightUnit.valueOf(unit);
                case "TemperatureUnit" -> TemperatureUnit.valueOf(unit);
                default -> throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
            };
        } catch (IllegalArgumentException e) {
            throw new QuantityMeasurementException("Invalid unit name: " + unit + ".", e);
        }

        return new Quantity<>(dto.value, measurableUnit);
    }

    private void validateSameMeasurementType(QuantityDTO thisQ, QuantityDTO thatQ, String operation) {
        if (!thisQ.measurementType.equals(thatQ.measurementType)) {
            throw new QuantityMeasurementException(
                    operation + " Error: Cannot perform operation between different measurement categories: "
                            + thisQ.measurementType + " and " + thatQ.measurementType);
        }
    }

    private void validateArithmetic(QuantityDTO thisQ, QuantityDTO thatQ, String operation) {
        if (!thisQ.measurementType.equals(thatQ.measurementType)) {
            throw new QuantityMeasurementException(
                    operation + " Error: Cannot perform arithmetic between different measurement categories: "
                            + thisQ.measurementType + " and " + thatQ.measurementType);
        }

        if ("TemperatureUnit".equals(thisQ.measurementType)) {
            throw new QuantityMeasurementException(
                    operation + " Error: Arithmetic operations are not supported for TemperatureUnit");
        }
    }

    private void validateTargetUnit(QuantityDTO thisQ, QuantityDTO targetUnitDTO, String operation) {
        if (targetUnitDTO == null) {
            throw new QuantityMeasurementException(operation + " Error: Target unit is required");
        }
        if (!thisQ.measurementType.equals(targetUnitDTO.measurementType)) {
            throw new QuantityMeasurementException(
                    operation + " Error: Target unit measurement type must match source measurement type");
        }
    }

    private String normalizeOperation(String operation) {
        try {
            return OperationType.fromString(operation).name();
        } catch (Exception e) {
            throw new QuantityMeasurementException("Invalid operation type: " + operation, e);
        }
    }

    private QuantityMeasurementDTO saveAndReturn(QuantityMeasurementEntity entity) {
        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }

    private QuantityMeasurementEntity createBaseEntity(QuantityDTO thisQ, QuantityDTO thatQ, OperationType operation) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.thisValue = getSafeValue(thisQ);
        entity.thisUnit = getSafeUnit(thisQ);
        entity.thisMeasurementType = getSafeMeasurementType(thisQ);
        entity.thatValue = getSafeValue(thatQ);
        entity.thatUnit = getSafeUnit(thatQ);
        entity.thatMeasurementType = getSafeMeasurementType(thatQ);
        entity.operation = operation.name();
        return entity;
    }

    private void saveError(OperationType operation, QuantityDTO thisQ, QuantityDTO thatQ, String errorMessage) {
        try {
            QuantityMeasurementEntity entity = createBaseEntity(thisQ, thatQ, operation);
            entity.isError = true;
            entity.errorMessage = errorMessage;
            repository.save(entity);
        } catch (Exception ignored) {
            // Do not mask the primary business exception.
        }
    }

    private double getSafeValue(QuantityDTO quantityDTO) {
        if (quantityDTO == null || quantityDTO.value == null) {
            return 0;
        }
        return quantityDTO.value;
    }

    private String getSafeUnit(QuantityDTO quantityDTO) {
        if (quantityDTO == null || quantityDTO.unit == null || quantityDTO.unit.isBlank()) {
            return DEFAULT_VALUE;
        }
        return quantityDTO.unit;
    }

    private String getSafeMeasurementType(QuantityDTO quantityDTO) {
        if (quantityDTO == null || quantityDTO.measurementType == null || quantityDTO.measurementType.isBlank()) {
            return DEFAULT_VALUE;
        }
        return quantityDTO.measurementType;
    }
}

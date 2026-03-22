package com.app.quantitymeasurement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	@Autowired
	private QuantityMeasurementRepository repository;

	// Helper to save entity
	private QuantityMeasurementDTO saveAndReturn(QuantityMeasurementEntity entity) {
		QuantityMeasurementEntity saved = repository.save(entity);
		return QuantityMeasurementDTO.fromEntity(saved);
	}

	// Error handler
	private QuantityMeasurementDTO handleError(String operation, Exception e) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
		entity.operation = operation;
		entity.isError = true;
		entity.errorMessage = e.getMessage();
		return saveAndReturn(entity);
	}

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO thisQ, QuantityDTO thatQ) {
		try {
			boolean result = thisQ.value == thatQ.value; // simplified logic

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
			entity.thisValue = thisQ.value;
			entity.thisUnit = thisQ.unit;
			entity.thisMeasurementType = thisQ.measurementType;

			entity.thatValue = thatQ.value;
			entity.thatUnit = thatQ.unit;
			entity.thatMeasurementType = thatQ.measurementType;

			entity.operation = OperationType.COMPARE.name();
			entity.resultString = result ? "Equal" : "Not Equal";

			return saveAndReturn(entity);

		} catch (Exception e) {
			return handleError("COMPARE", e);
		}
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO thisQ, QuantityDTO thatQ) {
		try {
			double result = thisQ.value; // simplified

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
			entity.thisValue = thisQ.value;
			entity.thisUnit = thisQ.unit;
			entity.thisMeasurementType = thisQ.measurementType;

			entity.thatUnit = thatQ.unit;
			entity.operation = OperationType.CONVERT.name();

			entity.resultValue = result;
			entity.resultUnit = thatQ.unit;

			return saveAndReturn(entity);

		} catch (Exception e) {
			return handleError("CONVERT", e);
		}
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ) {
		try {
			double result = thisQ.value + thatQ.value;

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
			entity.thisValue = thisQ.value;
			entity.thatValue = thatQ.value;
			entity.operation = OperationType.ADD.name();

			entity.resultValue = result;

			return saveAndReturn(entity);

		} catch (Exception e) {
			return handleError("ADD", e);
		}
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ, QuantityDTO target) {
		return add(thisQ, thatQ);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ) {
		try {
			double result = thisQ.value - thatQ.value;

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
			entity.thisValue = thisQ.value;
			entity.thatValue = thatQ.value;
			entity.operation = OperationType.SUBTRACT.name();

			entity.resultValue = result;

			return saveAndReturn(entity);

		} catch (Exception e) {
			return handleError("SUBTRACT", e);
		}
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ, QuantityDTO target) {
		return subtract(thisQ, thatQ);
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO thisQ, QuantityDTO thatQ) {
		try {
			double result = thisQ.value / thatQ.value;

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
			entity.thisValue = thisQ.value;
			entity.thatValue = thatQ.value;
			entity.operation = OperationType.DIVIDE.name();

			entity.resultValue = result;

			return saveAndReturn(entity);

		} catch (Exception e) {
			return handleError("DIVIDE", e);
		}
	}

	@Override
	public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
		return QuantityMeasurementDTO.fromList(
				repository.findByOperation(operation)
				);
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
		return QuantityMeasurementDTO.fromList(
				repository.findByThisMeasurementType(type)
				);
	}

	@Override
	public long getOperationCount(String operation) {
		return repository.countByOperationAndIsErrorFalse(operation);
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return QuantityMeasurementDTO.fromList(
				repository.findByIsErrorTrue()
				);
	}
}
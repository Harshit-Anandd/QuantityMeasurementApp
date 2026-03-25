package com.app.quantitymeasurement.service;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {

	QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	List<QuantityMeasurementDTO> getOperationHistory(String operation);

	List<QuantityMeasurementDTO> getMeasurementsByType(String type);

	long getOperationCount(String operation);

	List<QuantityMeasurementDTO> getErrorHistory();
}

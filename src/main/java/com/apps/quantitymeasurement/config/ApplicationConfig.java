package com.apps.quantitymeasurement.config;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.repository.QuantityMeasurementRepositoryImpl;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class ApplicationConfig {

	private static final QuantityMeasurementRepositoryImpl repository =
			new QuantityMeasurementRepositoryImpl();

	private static final QuantityMeasurementServiceImpl service =
			new QuantityMeasurementServiceImpl(repository);

	private static final QuantityMeasurementController controller =
			new QuantityMeasurementController(service);

	public static QuantityMeasurementController getController() {
		return controller;
	}
}
package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.config.DatabaseConnection;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuantityMeasurementRepositoryImpl
implements IQuantityMeasurementRepository {

	public void saveOperation(QuantityMeasurementEntity entity) {

		try {

			Connection connection = DatabaseConnection.getConnection();

			String query = """
					INSERT INTO quantity_operations
					(operation, operand1, operand2, result, timestamp)
					VALUES (?, ?, ?, ?, ?)
					""";

			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setString(1, entity.getOperation());
			stmt.setString(2, entity.getOperand1());
			stmt.setString(3, entity.getOperand2());
			stmt.setString(4, entity.getResult());
			stmt.setTimestamp(5,
					java.sql.Timestamp.valueOf(entity.getTimestamp()));

			stmt.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
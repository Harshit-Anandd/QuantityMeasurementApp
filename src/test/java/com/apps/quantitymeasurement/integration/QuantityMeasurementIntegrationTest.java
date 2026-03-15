package com.apps.quantitymeasurement.integration;

import com.apps.quantitymeasurement.config.ApplicationConfig;
import com.apps.quantitymeasurement.config.DatabaseConnection;
import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.model.LengthUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementIntegrationTest {

	private QuantityMeasurementController controller;

	@BeforeEach
	void setup() throws Exception {

		controller = ApplicationConfig.getController();

		// Clean database before each test
		Connection connection = DatabaseConnection.getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM quantity_operations");
	}

	@Test
	void testEndToEnd_AddOperation_PersistedToDatabase() throws Exception {

		QuantityDTO q1 = new QuantityDTO(1, LengthUnit.FEET);
		QuantityDTO q2 = new QuantityDTO(12, LengthUnit.INCH);

		double result = controller.add(q1, q2);

		assertEquals(2.0, result, 0.0001);

		Connection connection = DatabaseConnection.getConnection();
		Statement stmt = connection.createStatement();

		ResultSet rs =
				stmt.executeQuery("SELECT * FROM quantity_operations");

		assertTrue(rs.next());
		assertEquals("ADD", rs.getString("operation"));
	}

	@Test
	void testEndToEnd_SubtractOperation_PersistedToDatabase() throws Exception {

		QuantityDTO q1 = new QuantityDTO(3, LengthUnit.FEET);
		QuantityDTO q2 = new QuantityDTO(1, LengthUnit.FEET);

		double result = controller.subtract(q1, q2);

		assertEquals(2.0, result, 0.0001);

		Connection connection = DatabaseConnection.getConnection();
		Statement stmt = connection.createStatement();

		ResultSet rs =
				stmt.executeQuery("SELECT * FROM quantity_operations");

		assertTrue(rs.next());
		assertEquals("SUBTRACT", rs.getString("operation"));
	}

	@Test
	void testEndToEnd_DivideOperation_PersistedToDatabase() throws Exception {

		QuantityDTO q1 = new QuantityDTO(4, LengthUnit.FEET);
		QuantityDTO q2 = new QuantityDTO(2, LengthUnit.FEET);

		double result = controller.divide(q1, q2);

		assertEquals(2.0, result, 0.0001);

		Connection connection = DatabaseConnection.getConnection();
		Statement stmt = connection.createStatement();

		ResultSet rs =
				stmt.executeQuery("SELECT * FROM quantity_operations");

		assertTrue(rs.next());
		assertEquals("DIVIDE", rs.getString("operation"));
	}

	@Test
	void testEndToEnd_CompareOperation() {

		QuantityDTO q1 = new QuantityDTO(1, LengthUnit.FEET);
		QuantityDTO q2 = new QuantityDTO(12, LengthUnit.INCH);

		boolean result = controller.compare(q1, q2);

		assertTrue(result);
	}

	@Test
	void testEndToEnd_MultipleOperationsPersisted() throws Exception {

		controller.add(
				new QuantityDTO(1, LengthUnit.FEET),
				new QuantityDTO(12, LengthUnit.INCH));

		controller.subtract(
				new QuantityDTO(3, LengthUnit.FEET),
				new QuantityDTO(1, LengthUnit.FEET));

		controller.divide(
				new QuantityDTO(4, LengthUnit.FEET),
				new QuantityDTO(2, LengthUnit.FEET));

		Connection connection = DatabaseConnection.getConnection();
		Statement stmt = connection.createStatement();

		ResultSet rs =
				stmt.executeQuery("SELECT COUNT(*) FROM quantity_operations");

		rs.next();

		assertEquals(3, rs.getInt(1));
	}
}
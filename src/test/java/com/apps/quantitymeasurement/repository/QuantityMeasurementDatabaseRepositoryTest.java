package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementDatabaseRepositoryTest {

	private QuantityMeasurementRepositoryImpl repository;

	@BeforeEach
	void setup() {
		repository = new QuantityMeasurementRepositoryImpl();
	}

	@Test
	void testDatabaseRepository_SaveEntity() throws Exception {

		QuantityMeasurementEntity entity =
				new QuantityMeasurementEntity(
						"ADD",
						"1 FEET",
						"12 INCH",
						"2 FEET"
						);

		repository.saveOperation(entity);

		Connection connection =
				com.apps.quantitymeasurement.config.DatabaseConnection.getConnection();

		Statement stmt = connection.createStatement();

		ResultSet rs =
				stmt.executeQuery("SELECT COUNT(*) FROM quantity_operations");

		rs.next();

		assertTrue(rs.getInt(1) > 0);
	}

	@Test
	void testDatabaseRepository_RetrieveAllMeasurements() throws Exception {

		repository.saveOperation(
				new QuantityMeasurementEntity(
						"ADD","1 FEET","12 INCH","2 FEET"));

		repository.saveOperation(
				new QuantityMeasurementEntity(
						"SUBTRACT","3 FEET","1 FEET","2 FEET"));

		Connection conn =
				com.apps.quantitymeasurement.config.DatabaseConnection.getConnection();

		Statement stmt = conn.createStatement();

		ResultSet rs =
				stmt.executeQuery("SELECT * FROM quantity_operations");

		int count = 0;

		while(rs.next()){
			count++;
		}

		assertTrue(count >= 2);
	}

	@Test
	void testDatabaseRepository_DeleteAll() throws Exception {

		repository.saveOperation(
				new QuantityMeasurementEntity(
						"ADD","1 FEET","12 INCH","2 FEET"));

		Connection conn =
				com.apps.quantitymeasurement.config.DatabaseConnection.getConnection();

		Statement stmt = conn.createStatement();

		stmt.executeUpdate("DELETE FROM quantity_operations");

		ResultSet rs =
				stmt.executeQuery("SELECT COUNT(*) FROM quantity_operations");

		rs.next();

		assertEquals(0, rs.getInt(1));
	}

	@Test
	void testDatabaseSchema_TablesCreated() throws Exception {

		Connection conn =
				com.apps.quantitymeasurement.config.DatabaseConnection.getConnection();

		ResultSet rs =
				conn.getMetaData().getTables(null,null,
						"QUANTITY_OPERATIONS",null);

		assertTrue(rs.next());
	}
}
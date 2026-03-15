package com.apps.quantitymeasurement.config;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:h2:mem:quantitydb;DB_CLOSE_DELAY=-1";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {

        try {

            // Start H2 TCP server
            Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();

            // Start H2 Web Console
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

            Class.forName("org.h2.Driver");

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            Statement stmt = connection.createStatement();

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS quantity_operations (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    operation VARCHAR(50),
                    operand1 VARCHAR(50),
                    operand2 VARCHAR(50),
                    result VARCHAR(50),
                    timestamp TIMESTAMP
                )
            """);

            System.out.println("H2 DB running");
            System.out.println("Open console: http://localhost:8082");

        } catch (Exception e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
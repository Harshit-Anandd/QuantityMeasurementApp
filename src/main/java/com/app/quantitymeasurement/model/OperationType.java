package com.app.quantitymeasurement.model;

public enum OperationType {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    COMPARE,
    CONVERT;
	
    public String getDisplayName() {
        return this.name().toLowerCase();
    }

    public static OperationType fromString(String value) {
        return OperationType.valueOf(value.trim().toUpperCase());
    }
}

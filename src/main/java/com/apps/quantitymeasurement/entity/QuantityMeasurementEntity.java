package com.apps.quantitymeasurement.entity;

import java.time.LocalDateTime;

public class QuantityMeasurementEntity {

	private final String operation;
	private final String operand1;
	private final String operand2;
	private final String result;
	private final LocalDateTime timestamp;

	public QuantityMeasurementEntity(
			String operation,
			String operand1,
			String operand2,
			String result) {

		this.operation = operation;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.result = result;
		this.timestamp = LocalDateTime.now();
	}

	public String getOperation() { 
		return operation; 
	}
	public String getOperand1() { 
		return operand1; 
	}
	public String getOperand2() { 
		return operand2; 
	}
	public String getResult() { 
		return result; 
	}
	public LocalDateTime getTimestamp() { 
		return timestamp; 
	}
}
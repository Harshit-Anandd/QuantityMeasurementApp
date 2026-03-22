package com.app.quantitymeasurement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

@RestController
@RequestMapping("/api/v1/quantity")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // 🔁 COMPARE
    @PostMapping("/compare")
    public QuantityMeasurementDTO compare(@RequestBody QuantityInputDTO input) {
        return service.compare(input.getThisQuantity(), input.getThatQuantity());
    }

    // 🔁 CONVERT
    @PostMapping("/convert")
    public QuantityMeasurementDTO convert(@RequestBody QuantityInputDTO input) {
        return service.convert(input.getThisQuantity(), input.getTargetUnit());
    }

    // 🔁 ADD
    @PostMapping("/add")
    public QuantityMeasurementDTO add(@RequestBody QuantityInputDTO input) {
        return service.add(input.getThisQuantity(), input.getThatQuantity());
    }

    // 🔁 SUBTRACT
    @PostMapping("/subtract")
    public QuantityMeasurementDTO subtract(@RequestBody QuantityInputDTO input) {
        return service.subtract(input.getThisQuantity(), input.getThatQuantity());
    }

    // 🔁 DIVIDE
    @PostMapping("/divide")
    public QuantityMeasurementDTO divide(@RequestBody QuantityInputDTO input) {
        return service.divide(input.getThisQuantity(), input.getThatQuantity());
    }

    // 📜 HISTORY
    @GetMapping("/history/{operation}")
    public List<QuantityMeasurementDTO> getHistory(@PathVariable String operation) {
        return service.getOperationHistory(operation);
    }

    // 📊 COUNT
    @GetMapping("/count/{operation}")
    public long getCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }

    // ⚠️ ERRORS
    @GetMapping("/errors")
    public List<QuantityMeasurementDTO> getErrors() {
        return service.getErrorHistory();
    }
}
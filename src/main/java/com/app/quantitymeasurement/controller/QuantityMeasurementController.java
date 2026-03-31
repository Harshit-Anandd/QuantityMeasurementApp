package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    private static final Logger logger = Logger.getLogger(QuantityMeasurementController.class.getName());

    private static final String EX_FEET_INCH = """
            {
              "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
              "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
            }
            """;

    private static final String EX_YARD_FEET = """
            {
              "thisQuantityDTO": {"value": 1.0, "unit": "YARDS", "measurementType": "LengthUnit"},
              "thatQuantityDTO": {"value": 3.0, "unit": "FEET", "measurementType": "LengthUnit"}
            }
            """;

    private static final String EX_GALLON_LITRE = """
            {
              "thisQuantityDTO": {"value": 1.0, "unit": "GALLON", "measurementType": "VolumeUnit"},
              "thatQuantityDTO": {"value": 3.785, "unit": "LITRE", "measurementType": "VolumeUnit"}
            }
            """;

    private static final String EX_TEMP = """
            {
              "thisQuantityDTO": {"value": 212.0, "unit": "FAHRENHEIT", "measurementType": "TemperatureUnit"},
              "thatQuantityDTO": {"value": 100.0, "unit": "CELSIUS", "measurementType": "TemperatureUnit"}
            }
            """;

    private static final String EX_WITH_TARGET = """
            {
              "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
              "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"},
              "targetQuantityDTO": {"value": 0.0, "unit": "INCHES", "measurementType": "LengthUnit"}
            }
            """;

    @Autowired
    private IQuantityMeasurementService service;

    @Operation(
            summary = "Compare two quantities",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Feet = 12 Inches", value = EX_FEET_INCH),
                            @ExampleObject(name = "Yard = 3 Feet", value = EX_YARD_FEET),
                            @ExampleObject(name = "Gallon = Litres", value = EX_GALLON_LITRE),
                            @ExampleObject(name = "212F = 100C", value = EX_TEMP)
                    })
            )
    )
    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> performComparison(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("API request received: compare");
        try {
            QuantityMeasurementDTO response = service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO());
            logger.info("API compare completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API compare failed", e);
            throw e;
        }
    }

    @Operation(
            summary = "Convert quantity to target unit",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Feet -> Inches", value = EX_FEET_INCH),
                            @ExampleObject(name = "Yard -> Feet", value = EX_YARD_FEET),
                            @ExampleObject(name = "Gallon -> Litres", value = EX_GALLON_LITRE),
                            @ExampleObject(name = "212F -> 100C", value = EX_TEMP)
                    })
            )
    )
    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> performConversion(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("API request received: convert");
        try {
            QuantityMeasurementDTO response = service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO());
            logger.info("API convert completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API convert failed", e);
            throw e;
        }
    }

    @Operation(
            summary = "Add two quantities",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Feet + Inches", value = EX_FEET_INCH),
                            @ExampleObject(name = "Yard + Feet", value = EX_YARD_FEET),
                            @ExampleObject(name = "Gallon + Litres", value = EX_GALLON_LITRE)
                    })
            )
    )
    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> performAddition(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("API request received: add");
        try {
            QuantityMeasurementDTO response = service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO());
            logger.info("API add completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API add failed", e);
            throw e;
        }
    }

    @Operation(
            summary = "Add two quantities with a target unit",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Feet + Inches with target unit", value = EX_WITH_TARGET)
                    })
            )
    )
    @PostMapping("/add-with-target-unit")
    public ResponseEntity<QuantityMeasurementDTO> performAdditionWithTargetUnit(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("API request received: add-with-target-unit");
        try {
            QuantityMeasurementDTO response = service.add(
                    input.getThisQuantityDTO(),
                    input.getThatQuantityDTO(),
                    input.getTargetQuantityDTO());
            logger.info("API add-with-target-unit completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API add-with-target-unit failed", e);
            throw e;
        }
    }

    @Operation(
            summary = "Subtract two quantities",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Feet - Inches", value = EX_FEET_INCH),
                            @ExampleObject(name = "Yard - Feet", value = EX_YARD_FEET),
                            @ExampleObject(name = "Gallon - Litres", value = EX_GALLON_LITRE)
                    })
            )
    )
    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDTO> performSubtraction(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("API request received: subtract");
        try {
            QuantityMeasurementDTO response = service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO());
            logger.info("API subtract completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API subtract failed", e);
            throw e;
        }
    }

    @Operation(
            summary = "Subtract two quantities with target unit",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Feet - Inches with target unit", value = EX_WITH_TARGET)
                    })
            )
    )
    @PostMapping("/subtract-with-target-unit")
    public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("API request received: subtract-with-target-unit");
        try {
            QuantityMeasurementDTO response = service.subtract(
                    input.getThisQuantityDTO(),
                    input.getThatQuantityDTO(),
                    input.getTargetQuantityDTO());
            logger.info("API subtract-with-target-unit completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API subtract-with-target-unit failed", e);
            throw e;
        }
    }

    @Operation(
            summary = "Divide two quantities",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Feet / Inches", value = EX_FEET_INCH),
                            @ExampleObject(name = "Yard / Feet", value = EX_YARD_FEET),
                            @ExampleObject(name = "Gallon / Litres", value = EX_GALLON_LITRE)
                    })
            )
    )
    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDTO> performDivision(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("API request received: divide");
        try {
            QuantityMeasurementDTO response = service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO());
            logger.info("API divide completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API divide failed", e);
            throw e;
        }
    }

    @Operation(
            summary = "Get operation history",
            description = "Valid operations: ADD, SUBTRACT, MULTIPLY, DIVIDE, CONVERT"
    )
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {
        logger.info(String.format("API request received: get operation history for %s", operation));
        try {
            return ResponseEntity.ok(service.getOperationHistory(operation));
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("API operation history lookup failed for %s", operation), e);
            throw e;
        }
    }

    @Operation(
            summary = "Get operation history by type",
            description = "Valid types: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit"
    )
    @GetMapping("/history/type/{type}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistoryByType(@PathVariable String type) {
        logger.info(String.format("API request received: get measurement type history for %s", type));
        try {
            return ResponseEntity.ok(service.getMeasurementsByType(type));
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("API measurement type history lookup failed for %s", type), e);
            throw e;
        }
    }

    @Operation(
            summary = "Get operation count",
            description = "Valid operations: ADD, SUBTRACT, MULTIPLY, DIVIDE, CONVERT"
    )
    @GetMapping("/count/{operation}")
    public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {
        logger.info(String.format("API request received: get count for %s", operation));
        try {
            return ResponseEntity.ok(service.getOperationCount(operation));
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("API operation count lookup failed for %s", operation), e);
            throw e;
        }
    }

    @Operation(summary = "Get errored operations history")
    @GetMapping("/history/errored")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErroredOperations() {
        logger.info("API request received: get errored history");
        try {
            return ResponseEntity.ok(service.getErrorHistory());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "API errored history lookup failed", e);
            throw e;
        }
    }
}

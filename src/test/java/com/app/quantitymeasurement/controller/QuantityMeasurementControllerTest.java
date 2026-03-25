package com.app.quantitymeasurement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void compareShouldReturnSuccessResponse() throws Exception {
        String body = """
                {
                  "thisQuantityDTO": {
                    "value": 1.0,
                    "unit": "FEET",
                    "measurementType": "LengthUnit"
                  },
                  "thatQuantityDTO": {
                    "value": 12.0,
                    "unit": "INCHES",
                    "measurementType": "LengthUnit"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("compare"))
                .andExpect(jsonPath("$.resultString").value("true"))
                .andExpect(jsonPath("$.error").value(false));
    }

    @Test
    void invalidUnitShouldReturnBadRequest() throws Exception {
        String body = """
                {
                  "thisQuantityDTO": {
                    "value": 1.0,
                    "unit": "INCHE",
                    "measurementType": "LengthUnit"
                  },
                  "thatQuantityDTO": {
                    "value": 12.0,
                    "unit": "INCHES",
                    "measurementType": "LengthUnit"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Quantity Measurement Error"))
                .andExpect(jsonPath("$.message").value("Unit must be valid for the specified measurement type"));
    }

    @Test
    void addWithDifferentMeasurementTypesShouldReturnBadRequest() throws Exception {
        String body = """
                {
                  "thisQuantityDTO": {
                    "value": 1.0,
                    "unit": "FEET",
                    "measurementType": "LengthUnit"
                  },
                  "thatQuantityDTO": {
                    "value": 1.0,
                    "unit": "KILOGRAM",
                    "measurementType": "WeightUnit"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Quantity Measurement Error"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString(
                        "Cannot perform arithmetic between different measurement categories")));
    }

    @Test
    void addWithTargetUnitShouldReturnSuccessResponse() throws Exception {
        String body = """
                {
                  "thisQuantityDTO": {
                    "value": 1.0,
                    "unit": "FEET",
                    "measurementType": "LengthUnit"
                  },
                  "thatQuantityDTO": {
                    "value": 12.0,
                    "unit": "INCHES",
                    "measurementType": "LengthUnit"
                  },
                  "targetQuantityDTO": {
                    "value": 0.0,
                    "unit": "INCHES",
                    "measurementType": "LengthUnit"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/add-with-target-unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("add"))
                .andExpect(jsonPath("$.resultUnit").value("INCHES"))
                .andExpect(jsonPath("$.resultValue").value(24.0));
    }

    @Test
    void divideByZeroShouldReturnInternalServerError() throws Exception {
        String body = """
                {
                  "thisQuantityDTO": {
                    "value": 1.0,
                    "unit": "FEET",
                    "measurementType": "LengthUnit"
                  },
                  "thatQuantityDTO": {
                    "value": 0.0,
                    "unit": "INCHES",
                    "measurementType": "LengthUnit"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/divide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Divide by zero"));
    }
}

package com.app.quantitymeasurement.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.app.quantitymeasurement.security.JwtAuthenticationFilter;

@WebMvcTest(
        value = QuantityMeasurementController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtAuthenticationFilter.class
                )
        },
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class
        })
@AutoConfigureMockMvc(addFilters = false)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Autowired
    private ObjectMapper objectMapper;

    private QuantityInputDTO quantity1;
    private QuantityMeasurementDTO measurementResult;

    @BeforeEach
    void setup() {
        quantity1 = new QuantityInputDTO();
        quantity1.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"));
        quantity1.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LengthUnit"));

        measurementResult = new QuantityMeasurementDTO();
        measurementResult.thisValue = quantity1.getThisQuantityDTO().value;
        measurementResult.thisUnit = quantity1.getThisQuantityDTO().unit;
        measurementResult.thisMeasurementType = quantity1.getThisQuantityDTO().measurementType;
        measurementResult.thatValue = quantity1.getThatQuantityDTO().value;
        measurementResult.thatUnit = quantity1.getThatQuantityDTO().unit;
        measurementResult.thatMeasurementType = quantity1.getThatQuantityDTO().measurementType;
    }

    @Test
    void testCompareQuantitiesSuccess() throws Exception {
        measurementResult.operation = "compare";
        measurementResult.resultString = "true";
        measurementResult.resultValue = 0.0;
        measurementResult.resultUnit = null;
        measurementResult.resultMeasurementType = null;
        measurementResult.error = false;

        when(service.compare(any(QuantityDTO.class), any(QuantityDTO.class)))
                .thenReturn(measurementResult);

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quantity1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    void testAddQuantitiesSuccess() throws Exception {
        measurementResult.operation = "add";
        measurementResult.resultValue = 2.0;
        measurementResult.resultUnit = "FEET";
        measurementResult.resultMeasurementType = "LengthUnit";
        measurementResult.error = false;

        when(service.add(any(QuantityDTO.class), any(QuantityDTO.class)))
                .thenReturn(measurementResult);

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quantity1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0));
    }

    @Test
    void testGetOperationHistorySuccess() throws Exception {
        when(service.getOperationHistory("COMPARE"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetOperationCountSuccess() throws Exception {
        when(service.getOperationCount("COMPARE"))
                .thenReturn(0L);

        mockMvc.perform(get("/api/v1/quantities/count/COMPARE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}

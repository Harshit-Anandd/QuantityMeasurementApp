package com.app.quantitymeasurement;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.quantitymeasurement.model.AuthResponse;
import com.app.quantitymeasurement.model.LoginRequest;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.RegisterRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:quantitymeasurementtestdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.h2.console.enabled=true"
})
class QuantityMeasurementApplicationTests {

    private static final String TEST_USER_EMAIL = "integration.tester@quantity.app";
    private static final String TEST_USER_PASSWORD = "StrongPass123!";
    private static String bearerToken;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/quantities";
    }

    private String authBaseUrl() {
        return "http://localhost:" + port + "/api/v1/auth";
    }

    private QuantityInputDTO input(
            double thisValue, String thisUnit, String thisMeasurementType,
            double thatValue, String thatUnit, String thatMeasurementType) {
        QuantityInputDTO inputDTO = new QuantityInputDTO();
        inputDTO.setThisQuantityDTO(new QuantityDTO(thisValue, thisUnit, thisMeasurementType));
        inputDTO.setThatQuantityDTO(new QuantityDTO(thatValue, thatUnit, thatMeasurementType));
        return inputDTO;
    }

    private QuantityInputDTO inputWithTarget(
            double thisValue, String thisUnit, String thisMeasurementType,
            double thatValue, String thatUnit, String thatMeasurementType,
            double targetValue, String targetUnit, String targetMeasurementType) {
        QuantityInputDTO inputDTO = new QuantityInputDTO();
        inputDTO.setThisQuantityDTO(new QuantityDTO(thisValue, thisUnit, thisMeasurementType));
        inputDTO.setThatQuantityDTO(new QuantityDTO(thatValue, thatUnit, thatMeasurementType));
        inputDTO.setTargetQuantityDTO(new QuantityDTO(targetValue, targetUnit, targetMeasurementType));
        return inputDTO;
    }

    private HttpEntity<QuantityInputDTO> jsonEntity(QuantityInputDTO body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getBearerToken());
        return new HttpEntity<>(body, headers);
    }

    private <T> ResponseEntity<T> authorizedGet(String url, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getBearerToken());
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
    }

    private String getBearerToken() {
        if (bearerToken != null && !bearerToken.isBlank()) {
            return bearerToken;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.fullName = "Integration Tester";
        registerRequest.email = TEST_USER_EMAIL;
        registerRequest.password = TEST_USER_PASSWORD;

        ResponseEntity<String> registerResponse = restTemplate.postForEntity(
                authBaseUrl() + "/register",
                new HttpEntity<>(registerRequest, headers),
                String.class);

        assertThat(registerResponse.getStatusCode().is2xxSuccessful()
                || registerResponse.getStatusCode() == HttpStatus.CONFLICT).isTrue();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = TEST_USER_EMAIL;
        loginRequest.password = TEST_USER_PASSWORD;

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                authBaseUrl() + "/login",
                new HttpEntity<>(loginRequest, headers),
                AuthResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
        assertThat(loginResponse.getBody().accessToken).isNotBlank();

        bearerToken = loginResponse.getBody().accessToken;
        return bearerToken;
    }

    @Test
    @Order(1)
    @DisplayName("Application context loads and test server starts")
    void contextLoads() {
        assertThat(restTemplate).isNotNull();
        assertThat(port).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @DisplayName("POST /compare - 1 foot equals 12 inches")
    void testCompareFootEqualsInches() {
        QuantityInputDTO body = input(1.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultString).isEqualTo("true");
    }

    @Test
    @Order(3)
    @DisplayName("POST /compare - 1 foot does not equal 1 inch")
    void testCompareFootNotEqualInch() {
        QuantityInputDTO body = input(1.0, "FEET", "LengthUnit", 1.0, "INCHES", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultString).isEqualTo("false");
    }

    @Test
    @Order(4)
    @DisplayName("POST /compare - 1 gallon equals 3.785 litres")
    void testCompareGallonEqualsLitres() {
        QuantityInputDTO body = input(1.0, "GALLON", "VolumeUnit", 3.785, "LITRE", "VolumeUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultString).isEqualTo("true");
    }

    @Test
    @Order(5)
    @DisplayName("POST /compare - 212 Fahrenheit equals 100 Celsius")
    void testCompareFahrenheitEqualsCelsius() {
        QuantityInputDTO body = input(212.0, "FAHRENHEIT", "TemperatureUnit", 100.0, "CELSIUS", "TemperatureUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultString).isEqualTo("true");
    }

    @Test
    @Order(6)
    @DisplayName("POST /convert - 100 Celsius to Fahrenheit")
    void testConvertCelsiusToFahrenheit() {
        QuantityInputDTO body = input(100.0, "CELSIUS", "TemperatureUnit", 0.0, "FAHRENHEIT", "TemperatureUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/convert",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultValue).isBetween(211.9, 212.1);
    }

    @Test
    @Order(7)
    @DisplayName("POST /add - 1 gallon + 3.785 litres = ~2 gallons")
    void testAddGallonAndLitres() {
        QuantityInputDTO body = input(1.0, "GALLON", "VolumeUnit", 3.785, "LITRE", "VolumeUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/add",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultValue).isBetween(1.99, 2.01);
    }

    @Test
    @Order(8)
    @DisplayName("POST /add-with-target-unit - 1 foot + 12 inches as inches")
    void testAddWithTargetUnitFootAndInchesToInches() {
        QuantityInputDTO body = inputWithTarget(
                1.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                0.0, "INCHES", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/add-with-target-unit",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultValue).isEqualTo(24.0);
    }

    @Test
    @Order(9)
    @DisplayName("POST /subtract - 2 feet minus 12 inches")
    void testSubtractFeetMinusInches() {
        QuantityInputDTO body = input(2.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/subtract",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultValue).isEqualTo(1.0);
    }

    @Test
    @Order(10)
    @DisplayName("POST /subtract-with-target-unit - 2 feet minus 12 inches as inches")
    void testSubtractWithTargetUnit() {
        QuantityInputDTO body = inputWithTarget(
                2.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                0.0, "INCHES", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/subtract-with-target-unit",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultValue).isEqualTo(12.0);
    }

    @Test
    @Order(11)
    @DisplayName("POST /divide - 1 yard divided by 1 foot = 3")
    void testDivideYardByFoot() {
        QuantityInputDTO body = input(1.0, "YARDS", "LengthUnit", 1.0, "FEET", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/divide",
                HttpMethod.POST,
                jsonEntity(body),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().resultValue).isEqualTo(3.0);
    }

    @Test
    @Order(12)
    @DisplayName("GET /history/operation/CONVERT returns history")
    void testGetHistoryByOperationConvert() {
        ResponseEntity<List> response = authorizedGet(
                baseUrl() + "/history/operation/CONVERT", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(13)
    @DisplayName("GET /history/type/TemperatureUnit returns history")
    void testGetHistoryByTypeTemperature() {
        ResponseEntity<List> response = authorizedGet(
                baseUrl() + "/history/type/TemperatureUnit", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(14)
    @DisplayName("GET /count/DIVIDE returns count > 0")
    void testGetOperationCountDivide() {
        ResponseEntity<Long> response = authorizedGet(
                baseUrl() + "/count/DIVIDE", Long.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isGreaterThan(0L);
    }

    @Test
    @Order(15)
    @DisplayName("POST /divide by zero then GET /history/errored")
    void testDivideByZeroThenErrorHistory() {
        QuantityInputDTO body = input(1.0, "YARDS", "LengthUnit", 0.0, "FEET", "LengthUnit");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/divide",
                HttpMethod.POST,
                jsonEntity(body),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("Divide by zero");

        ResponseEntity<List> errorHistoryResponse = authorizedGet(
                baseUrl() + "/history/errored", List.class);

        assertThat(errorHistoryResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(errorHistoryResponse.getBody()).isNotNull();
        assertThat(errorHistoryResponse.getBody()).isNotEmpty();
    }

    @Test
    @Order(16)
    @DisplayName("POST /compare invalid unit returns 400")
    void testCompareUnitValidationFails() {
        QuantityInputDTO body = input(1.0, "FOOT", "LengthUnit", 12.0, "INCHES", "LengthUnit");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/compare",
                HttpMethod.POST,
                jsonEntity(body),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Unit must be valid for the specified measurement type");
    }

    @Test
    @Order(17)
    @DisplayName("POST /compare invalid measurement type returns 400")
    void testCompareTypeValidationFails() {
        QuantityInputDTO body = input(1.0, "FEET", "Length", 12.0, "INCHES", "Length");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/compare",
                HttpMethod.POST,
                jsonEntity(body),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains(
                "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit");
    }

    @Test
    @Order(18)
    @DisplayName("GET /swagger-ui.html loads Swagger UI")
    void testSwaggerUiLoads() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/swagger-ui.html",
                String.class);

        assertThat(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection()).isTrue();
    }

    @Test
    @Order(19)
    @DisplayName("GET /api-docs returns OpenAPI schema")
    void testOpenApiDocumentationLoads() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api-docs",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("\"openapi\"");
        assertThat(response.getBody()).contains("/api/v1/quantities/compare");
    }

    @Test
    @Order(20)
    @DisplayName("GET /actuator/health returns UP")
    void testActuatorHealthEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/actuator/health",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("\"status\":\"UP\"");
    }

    @Test
    @Order(21)
    @DisplayName("GET /actuator/metrics returns metrics payload")
    void testActuatorMetricsEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/actuator/metrics",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("\"names\"");
    }

    @Test
    @Order(22)
    @DisplayName("POST /compare with Accept application/xml returns XML")
    void testContentNegotiationXml() {
        QuantityInputDTO body = input(1.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        headers.setBearerAuth(getBearerToken());

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/compare",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isNotNull();
        assertThat(response.getHeaders().getContentType().toString()).contains("xml");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("<resultString>true</resultString>");
    }

    @Test
    @Order(23)
    @DisplayName("GET /h2-console is reachable in development profile")
    void testH2ConsoleLoads() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/h2-console",
                String.class);

        assertThat(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection()).isTrue();
    }
}

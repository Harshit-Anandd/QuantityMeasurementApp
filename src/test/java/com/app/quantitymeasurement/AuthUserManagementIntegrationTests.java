package com.app.quantitymeasurement;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.quantitymeasurement.model.AuthResponse;
import com.app.quantitymeasurement.model.LoginRequest;
import com.app.quantitymeasurement.model.RegisterRequest;
import com.app.quantitymeasurement.model.UserDTO;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:authusertestdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.h2.console.enabled=true"
})
class AuthUserManagementIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authBaseUrl() {
        return "http://localhost:" + port + "/api/v1/auth";
    }

    private String usersBaseUrl() {
        return "http://localhost:" + port + "/api/v1/users";
    }

    private String quantitiesBaseUrl() {
        return "http://localhost:" + port + "/api/v1/quantities";
    }

    private AuthResponse registerAndLogin(String fullName, String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.fullName = fullName;
        registerRequest.email = email;
        registerRequest.password = password;

        ResponseEntity<AuthResponse> registerResponse = restTemplate.exchange(
                authBaseUrl() + "/register",
                HttpMethod.POST,
                new HttpEntity<>(registerRequest, headers),
                AuthResponse.class);

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(registerResponse.getBody()).isNotNull();
        assertThat(registerResponse.getBody().accessToken).isNotBlank();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = email;
        loginRequest.password = password;

        ResponseEntity<AuthResponse> loginResponse = restTemplate.exchange(
                authBaseUrl() + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginRequest, headers),
                AuthResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
        assertThat(loginResponse.getBody().accessToken).isNotBlank();
        return loginResponse.getBody();
    }

    @Test
    @DisplayName("Register and login should return JWT and user profile")
    void testRegisterAndLogin() {
        String email = "authuser+" + Instant.now().toEpochMilli() + "@quantity.app";
        String password = "StrongPass123!";

        AuthResponse response = registerAndLogin("Auth User", email, password);

        assertThat(response.tokenType).isEqualTo("Bearer");
        assertThat(response.expiresInSeconds).isGreaterThan(0);
        assertThat(response.user).isNotNull();
        assertThat(response.user.email).isEqualTo(email);
    }

    @Test
    @DisplayName("Protected quantity endpoint should return 401 without JWT")
    void testQuantityEndpointUnauthorizedWithoutToken() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                quantitiesBaseUrl() + "/count/COMPARE",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("User can fetch own profile with valid JWT")
    void testGetCurrentUserProfile() {
        String email = "profileuser+" + Instant.now().toEpochMilli() + "@quantity.app";
        String password = "StrongPass123!";
        AuthResponse authResponse = registerAndLogin("Profile User", email, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authResponse.accessToken);

        ResponseEntity<UserDTO> meResponse = restTemplate.exchange(
                usersBaseUrl() + "/me",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserDTO.class);

        assertThat(meResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(meResponse.getBody()).isNotNull();
        assertThat(meResponse.getBody().email).isEqualTo(email);
    }

    @Test
    @DisplayName("Non-admin user cannot list all users")
    void testListUsersForbiddenForNonAdmin() {
        String email = "normaluser+" + Instant.now().toEpochMilli() + "@quantity.app";
        String password = "StrongPass123!";
        AuthResponse authResponse = registerAndLogin("Normal User", email, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authResponse.accessToken);

        ResponseEntity<String> usersResponse = restTemplate.exchange(
                usersBaseUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);

        assertThat(usersResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}

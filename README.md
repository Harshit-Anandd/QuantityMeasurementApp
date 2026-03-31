# Quantity Measurement App - UC17

## Overview

This project implements **UC17: Spring Framework Integration - REST Services and JPA for Quantity Measurement**.
It migrates the application to Spring Boot while preserving UC1-UC16 business behavior.

## Key Capabilities

- Spring Boot application with embedded Tomcat.
- REST endpoints under `/api/v1/quantities`.
- Persistence through Spring Data JPA.
- Validation and centralized exception handling with `@ControllerAdvice`.
- OpenAPI/Swagger documentation.
- Spring Actuator health and metrics endpoints.
- JWT-based API security.
- OAuth2 login with Google and automatic account provisioning.
- User management APIs (`/api/v1/users`) with admin role controls.

## API and Docs Endpoints

- REST base: `/api/v1/quantities`
- Auth base: `/api/v1/auth`
  - Local register: `POST /api/v1/auth/register`
  - Local login: `POST /api/v1/auth/login`
  - Google OAuth2 entrypoint: `/oauth2/authorization/google`
  - OAuth2 success behavior:
    - If `OAUTH2_SUCCESS_REDIRECT_URI` is blank, callback returns JSON `AuthResponse` directly.
    - If set, callback redirects to that URI with token query params.
- User base: `/api/v1/users`
  - Current user: `GET /api/v1/users/me`
  - Update profile/password: `PUT /api/v1/users/me`
  - Admin list users: `GET /api/v1/users`
  - Admin update role: `PUT /api/v1/users/{userId}/role`
- Swagger UI: `/swagger-ui.html`
- OpenAPI docs: `/api-docs`
- H2 Console (dev): `/h2-console`
- Actuator health: `/actuator/health`
- Actuator metrics: `/actuator/metrics`

## Profiles

- `application.properties`: development defaults (H2 in-memory DB, debug logs).
- `application-prod.properties`: production-oriented MySQL settings.

## Build and Test

```bash
mvn clean test
```

```bash
mvn spring-boot:run
```

Test coverage includes controller tests (`@WebMvcTest` + MockMvc), integration tests (`@SpringBootTest` + TestRestTemplate), and repository tests (`@DataJpaTest`).

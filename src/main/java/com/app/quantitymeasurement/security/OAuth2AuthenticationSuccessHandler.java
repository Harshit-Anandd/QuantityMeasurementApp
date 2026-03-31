package com.app.quantitymeasurement.security;

import com.app.quantitymeasurement.exception.ErrorResponse;
import com.app.quantitymeasurement.model.AuthResponse;
import com.app.quantitymeasurement.service.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    private final UserAuthService userAuthService;
    private final ObjectMapper objectMapper;

    @Value("${app.security.oauth2.success-redirect-uri:}")
    private String successRedirectUri;

    public OAuth2AuthenticationSuccessHandler(UserAuthService userAuthService, ObjectMapper objectMapper) {
        this.userAuthService = userAuthService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            String email = Optional.ofNullable(oAuth2User.getAttribute("email"))
                    .map(Object::toString)
                    .orElse(null);
            String fullName = Optional.ofNullable(oAuth2User.getAttribute("name"))
                    .map(Object::toString)
                    .orElse(email);
            String providerId = Optional.ofNullable(oAuth2User.getAttribute("sub"))
                    .map(Object::toString)
                    .orElse(null);

            // Validate email is available
            if (email == null || email.isBlank()) {
                logger.warn("Google OAuth2: Email not available in user info");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpServletResponse.SC_BAD_REQUEST,
                    "OAUTH2_EMAIL_UNAVAILABLE",
                    "Google account does not provide email. Please grant email permission and try again.",
                    request.getRequestURI());
                objectMapper.writeValue(response.getOutputStream(), error);
                return;
            }

            // Validate provider ID for security
            if (providerId == null || providerId.isBlank()) {
                logger.warn("Google OAuth2: Provider ID (sub) not available");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpServletResponse.SC_BAD_REQUEST,
                    "OAUTH2_INVALID_PROVIDER",
                    "Google account identification failed. Please try again.",
                    request.getRequestURI());
                objectMapper.writeValue(response.getOutputStream(), error);
                return;
            }

            AuthResponse authResponse = userAuthService.handleGoogleLogin(email, fullName, providerId);
            clearAuthenticationAttributes(request);

            if (successRedirectUri != null && !successRedirectUri.isBlank()) {
                // Redirect with token as query parameter (frontend will extract)
                // NOTE: For production, consider using POST redirect or HTTP-only cookies
                String targetUri = UriComponentsBuilder.fromUriString(successRedirectUri)
                        .queryParam("accessToken", authResponse.accessToken)
                        .queryParam("tokenType", authResponse.tokenType)
                        .queryParam("expiresIn", authResponse.expiresInSeconds)
                        .build()
                        .toUriString();

                logger.info("OAuth2 success: Redirecting to {}", successRedirectUri);
                getRedirectStrategy().sendRedirect(request, response, targetUri);
                return;
            }

            // Fallback: Return JSON response directly
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(), authResponse);
            logger.info("OAuth2 success: User {} authenticated", email);

        } catch (Exception ex) {
            logger.error("OAuth2 authentication failed", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "OAUTH2_ERROR",
                "Authentication failed. Please try again.",
                request.getRequestURI());
            objectMapper.writeValue(response.getOutputStream(), error);
        }
    }
}

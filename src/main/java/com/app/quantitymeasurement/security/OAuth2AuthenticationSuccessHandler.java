package com.app.quantitymeasurement.security;

import com.app.quantitymeasurement.model.AuthResponse;
import com.app.quantitymeasurement.service.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

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

        if (email == null || email.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Google email is not available");
            return;
        }

        AuthResponse authResponse = userAuthService.handleGoogleLogin(email, fullName, providerId);
        clearAuthenticationAttributes(request);

        if (successRedirectUri != null && !successRedirectUri.isBlank()) {
            String targetUri = UriComponentsBuilder.fromUriString(successRedirectUri)
                    .queryParam("accessToken", authResponse.accessToken)
                    .queryParam("tokenType", authResponse.tokenType)
                    .queryParam("expiresIn", authResponse.expiresInSeconds)
                    .build()
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUri);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }
}

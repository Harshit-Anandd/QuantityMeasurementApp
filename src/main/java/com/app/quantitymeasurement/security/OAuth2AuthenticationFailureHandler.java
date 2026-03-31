package com.app.quantitymeasurement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.security.oauth2.failure-redirect-uri:}")
    private String failureRedirectUri;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        if (failureRedirectUri != null && !failureRedirectUri.isBlank()) {
            String targetUri = UriComponentsBuilder.fromUriString(failureRedirectUri)
                    .queryParam("error", "oauth2_failure")
                    .queryParam("message", exception.getMessage())
                    .build()
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUri);
            return;
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}


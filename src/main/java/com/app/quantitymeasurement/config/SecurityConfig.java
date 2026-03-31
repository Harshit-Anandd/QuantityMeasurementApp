package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.security.JwtAuthenticationFilter;
import com.app.quantitymeasurement.security.OAuth2AuthenticationFailureHandler;
import com.app.quantitymeasurement.security.OAuth2AuthenticationSuccessHandler;
import com.app.quantitymeasurement.security.RestAuthenticationEntryPoint;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
            OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
            RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // OAuth2 requires session support for state parameter during authorization flow
                // Changed from IF_REQUIRED to ALWAYS for better OAuth2 state management
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/api-docs/**",
                                "/h2-console/**",
                                "/error")
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/actuator/health",
                                "/actuator/info",
                                "/actuator/metrics",
                                "/actuator/metrics/**")
                        .permitAll()
                        .requestMatchers("/api/v1/users/**")
                        .authenticated()
                        .requestMatchers("/api/v1/quantities/**")
                        .authenticated()
                        .anyRequest()
                        .authenticated())
                // OAuth2 Login configuration with success handler
                .oauth2Login(oauth2 -> oauth2
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                    // Enable authorization request repository for CSRF protection during OAuth2 flow
                    .authorizationEndpoint(endpoint -> endpoint
                        .authorizationRequestRepository(authorizationRequestRepository())))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(Customizer.withDefaults());

        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return restAuthenticationEntryPoint;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
}

package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.exception.UserManagementException;
import com.app.quantitymeasurement.model.AuthProvider;
import com.app.quantitymeasurement.model.AuthResponse;
import com.app.quantitymeasurement.model.LoginRequest;
import com.app.quantitymeasurement.model.RegisterRequest;
import com.app.quantitymeasurement.model.UpdateUserProfileRequest;
import com.app.quantitymeasurement.model.UpdateUserRoleRequest;
import com.app.quantitymeasurement.model.UserDTO;
import com.app.quantitymeasurement.model.UserEntity;
import com.app.quantitymeasurement.model.UserRole;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtService;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserAuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.email);
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserManagementException("Email is already registered", HttpStatus.CONFLICT);
        }

        UserEntity user = new UserEntity();
        user.email = email;
        user.fullName = request.fullName.trim();
        user.passwordHash = passwordEncoder.encode(request.password);
        user.provider = AuthProvider.LOCAL;
        user.role = UserRole.USER;
        user.enabled = true;

        return buildAuthResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String email = normalizeEmail(request.email);
        UserEntity user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserManagementException("Invalid email or password", HttpStatus.UNAUTHORIZED));

        if (!user.enabled) {
            throw new UserManagementException("User account is disabled", HttpStatus.FORBIDDEN);
        }

        if (user.passwordHash == null || user.passwordHash.isBlank()) {
            throw new UserManagementException("Use Google login for this account", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw new UserManagementException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse handleGoogleLogin(String email, String fullName, String providerId) {
        String normalizedEmail = normalizeEmail(email);
        UserEntity user = userRepository.findByEmailIgnoreCase(normalizedEmail).orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.email = normalizedEmail;
            newUser.role = UserRole.USER;
            newUser.enabled = true;
            newUser.provider = AuthProvider.GOOGLE;
            return newUser;
        });

        if (fullName != null && !fullName.isBlank()) {
            user.fullName = fullName.trim();
        } else if (user.fullName == null || user.fullName.isBlank()) {
            user.fullName = normalizedEmail;
        }

        if (user.provider == AuthProvider.GOOGLE || user.provider == null) {
            user.provider = AuthProvider.GOOGLE;
            user.providerId = providerId;
        }

        if (user.role == null) {
            user.role = UserRole.USER;
        }

        return buildAuthResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        return UserDTO.fromEntity(getCurrentUserEntity());
    }

    @Transactional
    public UserDTO updateCurrentUser(UpdateUserProfileRequest request) {
        UserEntity user = getCurrentUserEntity();

        if (request.fullName != null && !request.fullName.isBlank()) {
            user.fullName = request.fullName.trim();
        }

        if (request.newPassword != null && !request.newPassword.isBlank()) {
            if (user.provider != AuthProvider.LOCAL) {
                throw new UserManagementException(
                        "Password update is only supported for locally registered users",
                        HttpStatus.BAD_REQUEST);
            }
            if (request.currentPassword == null || request.currentPassword.isBlank()) {
                throw new UserManagementException("Current password is required", HttpStatus.BAD_REQUEST);
            }
            if (!passwordEncoder.matches(request.currentPassword, user.passwordHash)) {
                throw new UserManagementException("Current password is incorrect", HttpStatus.UNAUTHORIZED);
            }
            user.passwordHash = passwordEncoder.encode(request.newPassword);
        }

        return UserDTO.fromEntity(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(user -> user.id))
                .map(UserDTO::fromEntity)
                .toList();
    }

    @Transactional
    public UserDTO updateUserRole(Long userId, UpdateUserRoleRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserManagementException("User not found", HttpStatus.NOT_FOUND));

        user.role = request.role;
        return UserDTO.fromEntity(userRepository.save(user));
    }

    private AuthResponse buildAuthResponse(UserEntity user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.accessToken = jwtService.generateAccessToken(user);
        authResponse.expiresInSeconds = jwtService.getAccessTokenTtlSeconds();
        authResponse.user = UserDTO.fromEntity(user);
        return authResponse;
    }

    private UserEntity getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new UserManagementException("Authentication required", HttpStatus.UNAUTHORIZED);
        }

        String email = normalizeEmail(authentication.getName());
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserManagementException("Authenticated user not found", HttpStatus.NOT_FOUND));
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new UserManagementException("Email is required", HttpStatus.BAD_REQUEST);
        }
        return email.trim().toLowerCase();
    }
}

package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.UpdateUserProfileRequest;
import com.app.quantitymeasurement.model.UpdateUserRoleRequest;
import com.app.quantitymeasurement.model.UserDTO;
import com.app.quantitymeasurement.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "User profile and administration APIs")
public class UserController {

    private final UserAuthService userAuthService;

    public UserController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Operation(summary = "Get details for the currently authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> currentUser() {
        return ResponseEntity.ok(userAuthService.getCurrentUser());
    }

    @Operation(summary = "Update current user profile and optionally password")
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateCurrentUser(@Valid @RequestBody UpdateUserProfileRequest request) {
        return ResponseEntity.ok(userAuthService.updateCurrentUser(request));
    }

    @Operation(summary = "List all users (admin only)")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userAuthService.getAllUsers());
    }

    @Operation(summary = "Update user role (admin only)")
    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUserRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRoleRequest request) {
        return ResponseEntity.ok(userAuthService.updateUserRole(userId, request));
    }
}

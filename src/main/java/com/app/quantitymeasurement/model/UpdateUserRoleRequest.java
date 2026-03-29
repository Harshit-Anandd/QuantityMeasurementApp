package com.app.quantitymeasurement.model;

import jakarta.validation.constraints.NotNull;
public class UpdateUserRoleRequest {

    @NotNull(message = "Role is required")
    public UserRole role;
}

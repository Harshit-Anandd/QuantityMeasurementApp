package com.app.quantitymeasurement.model;

import jakarta.validation.constraints.Size;
public class UpdateUserProfileRequest {

    @Size(min = 1, max = 120, message = "Full name must be between 1 and 120 characters")
    public String fullName;

    @Size(min = 8, max = 100, message = "Current password must be between 8 and 100 characters")
    public String currentPassword;

    @Size(min = 8, max = 100, message = "New password must be between 8 and 100 characters")
    public String newPassword;
}

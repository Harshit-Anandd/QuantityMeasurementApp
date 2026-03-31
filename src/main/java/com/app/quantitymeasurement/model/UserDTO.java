package com.app.quantitymeasurement.model;

import java.time.LocalDateTime;
public class UserDTO {

    public Long id;
    public String email;
    public String fullName;
    public UserRole role;
    public AuthProvider provider;
    public boolean enabled;
    public LocalDateTime createdAt;

    public static UserDTO fromEntity(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.id = entity.id;
        dto.email = entity.email;
        dto.fullName = entity.fullName;
        dto.role = entity.role;
        dto.provider = entity.provider;
        dto.enabled = entity.enabled;
        dto.createdAt = entity.createdAt;
        return dto;
    }
}

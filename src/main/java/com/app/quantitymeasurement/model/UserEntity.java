package com.app.quantitymeasurement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "app_user",
        indexes = {
                @Index(name = "idx_app_user_email", columnList = "email"),
                @Index(name = "idx_app_user_role", columnList = "role")
        }
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 190)
    public String email;

    @Column(name = "password_hash")
    public String passwordHash;

    @Column(name = "full_name", nullable = false)
    public String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public AuthProvider provider = AuthProvider.LOCAL;

    @Column(name = "provider_id")
    public String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserRole role = UserRole.USER;

    @Column(nullable = false)
    public boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

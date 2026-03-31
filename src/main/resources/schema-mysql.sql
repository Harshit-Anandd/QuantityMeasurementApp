CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(190) NOT NULL,
    password_hash VARCHAR(255) NULL,
    full_name VARCHAR(255) NOT NULL,
    provider ENUM('LOCAL','GOOGLE') NOT NULL,
    provider_id VARCHAR(255) NULL,
    role ENUM('USER','ADMIN') NOT NULL,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_user_email (email),
    INDEX idx_app_user_email (email),
    INDEX idx_app_user_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT NOT NULL AUTO_INCREMENT,
    this_value DOUBLE NOT NULL,
    this_unit VARCHAR(255) NOT NULL,
    this_measurement_type VARCHAR(255) NOT NULL,
    that_value DOUBLE NOT NULL,
    that_unit VARCHAR(255) NOT NULL,
    that_measurement_type VARCHAR(255) NOT NULL,
    operation VARCHAR(255) NOT NULL,
    result_value DOUBLE NULL,
    result_unit VARCHAR(255) NULL,
    result_measurement_type VARCHAR(255) NULL,
    result_string VARCHAR(255) NULL,
    is_error TINYINT(1) NULL DEFAULT 0,
    error_message VARCHAR(255) NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_operation (operation),
    INDEX idx_measurement_type (this_measurement_type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

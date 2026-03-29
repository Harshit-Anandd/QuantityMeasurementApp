package com.app.quantitymeasurement.model;

public class AuthResponse {

    public String tokenType = "Bearer";
    public String accessToken;
    public long expiresInSeconds;
    public UserDTO user;
}

package com.example.lms.dto.auth;

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";

    public AuthResponse() {}
    public AuthResponse(String token) { this.token = token; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
}

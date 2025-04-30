package com.holidaymatrix.dto;

public class RegisterRequest {
    private String username;
    private String password;
    private String role;

    // Constructeur vide
    public RegisterRequest() {}

    // Getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

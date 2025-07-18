package com.eindopdracht.DJCorner.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public class AuthRequestDto {
    @NotBlank
    public String username;

    @NotBlank
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

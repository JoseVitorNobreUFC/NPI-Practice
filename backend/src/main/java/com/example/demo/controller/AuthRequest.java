package com.example.demo.controller;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;

    public AuthRequest() {

    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Schema(hidden = true)
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.sprintproject.model;

public class User {
    private String email;
    private boolean isActive;

    public User() {
        this.email = "";
        this.isActive = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

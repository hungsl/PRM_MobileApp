package com.example.recycleview.login;

public class LoginRequest {
    private String username;
    private String passwordHash;

    public LoginRequest(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getter và Setter (cần cho Gson)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}

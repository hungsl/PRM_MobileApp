package com.example.recycleview.login;

public class UserResponse {
    private String username; // phải viết hoa chữ cái đầu để khớp với JSON
    private String role;

    // Constructor mặc định
    public UserResponse() {}

    // Constructor đầy đủ
    public UserResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // Getter & Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}




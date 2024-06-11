package com.ftn.sbnz.dto;

public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

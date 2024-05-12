package com.ftn.sbnz.dto;

public class UserGameDTO {
    private String username;

    public UserGameDTO() {
    }

    public UserGameDTO(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

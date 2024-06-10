package com.ftn.sbnz.dto;

import com.ftn.sbnz.model.enums.UserType;

public class CreateUserRequestDTO {

    private UserType userType;
    private String username;
    private String password;


    public CreateUserRequestDTO() {
    }

    public CreateUserRequestDTO(UserType userType, String username, String password) {
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
}

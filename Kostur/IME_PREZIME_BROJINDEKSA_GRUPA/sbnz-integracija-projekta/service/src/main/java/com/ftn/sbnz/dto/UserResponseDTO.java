package com.ftn.sbnz.dto;

import com.ftn.sbnz.model.enums.UserType;

public class UserResponseDTO {
    private Long id;
    private String username;
    private UserType userType;

    public UserResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserResponseDTO(Long id, String username, UserType userType) {
        this.id = id;
        this.username = username;
        this.userType = userType;
    }
}

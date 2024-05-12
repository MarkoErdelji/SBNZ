package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


public  class TokenPrincipalModel {
    private Long id;
    private String email;
    private UserType role;

    public TokenPrincipalModel() {
    }

    public TokenPrincipalModel(Long id, String email, UserType role) {
        this.id = id;
        this.email = email;
        this.role = role;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }
}
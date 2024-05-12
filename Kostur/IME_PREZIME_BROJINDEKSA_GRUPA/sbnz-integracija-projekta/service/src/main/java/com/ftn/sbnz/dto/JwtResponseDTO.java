package com.ftn.sbnz.dto;

public class JwtResponseDTO {
    private String accessToken;

    public JwtResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public JwtResponseDTO() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

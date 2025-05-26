package com.teliolabs.tejas.ptn.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthContext {
    private String accessToken;
    private String refreshToken;
    
    public String getAccessToken() {
        return accessToken;
    }

    // Setter for accessToken
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    // Getter for refreshToken
    public String getRefreshToken() {
        return refreshToken;
    }

    // Setter for refreshToken
    public void setRefreshToken(Object object) {
        this.refreshToken = (String) object;
    }

}

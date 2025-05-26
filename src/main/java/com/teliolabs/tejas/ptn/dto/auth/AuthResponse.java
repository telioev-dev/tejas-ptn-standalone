package com.teliolabs.tejas.ptn.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponse {
    @JsonProperty("jwt")
    private String accessToken;

    @Override
    public String toString() {
        return "AuthResponse{" +
                "jwt='" + accessToken + '\'' +
                '}';
    }

	public Object getRefreshToken() {
		// TODO Auto-generated method stub
		return "AuthResponse{" +
                "jwt='" + accessToken + '\'' +
                '}';
	}
}

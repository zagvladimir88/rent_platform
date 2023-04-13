package com.zagvladimir.dto.requests.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;
}
package com.zagvladimir.controller.requests.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest {

    @NotBlank
    private String user_login;

    @NotBlank
    private String user_password;
}

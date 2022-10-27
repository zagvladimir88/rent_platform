package com.zagvladimir.controller.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest {

    @Schema(defaultValue = "strjke", type = "string" , description = "User Login")
    @NotBlank
    private String user_login;

    @Schema(defaultValue = "test", type = "string" , description = "User password")
    @NotBlank
    private String user_password;
}

package com.zagvladimir.controller.requests.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserChangeCredentialsRequest {
    @Schema(defaultValue = "testLogin", type = "string" , description = "User Login")
    @NotBlank
    @Size(min = 2,max = 100)
    private String userLogin;

    @Schema(defaultValue = "testPassword", type = "string" , description = "User Password")
    @NotBlank
    @Size(min = 6,max = 200)
    private String userPassword;
}

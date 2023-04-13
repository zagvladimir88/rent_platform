package com.zagvladimir.dto.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

	@Schema(defaultValue = "strjke", type = "string" , description = "login")
	@NotBlank
    private String username;

	@Schema(defaultValue = "test", type = "string" , description = "password")
	@NotBlank
    private String password;
}

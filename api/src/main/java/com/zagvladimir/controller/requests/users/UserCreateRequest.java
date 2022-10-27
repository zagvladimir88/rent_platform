package com.zagvladimir.controller.requests.users;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserCreateRequest {

    @Schema(defaultValue = "Vladimir", type = "string" , description = "User Name")
    @NotBlank
    @Size(min = 2,max = 25)
    private String username;

    @Schema(defaultValue = "Ivanov", type = "string" , description = "Last Name")
    @NotBlank
    @Size(min = 2,max = 32)
    private String lastName;

    @Schema(defaultValue = "testLogin", type = "string" , description = "User Login")
    @NotBlank
    @Size(min = 2,max = 100)
    private String userLogin;

    @Schema(defaultValue = "testPassword", type = "string" , description = "User Password")
    @NotBlank
    @Size(min = 6,max = 200)
    private String userPassword;

    @Schema(defaultValue = "2", type = "Long" , description = "Location id")
    private long locationId;

    @Schema(defaultValue = "19 4 33", type = "string" , description = "User address first line")
    private String locationDetails;

    @Schema(defaultValue = "+375291544444", type = "string" , description = "User mobile number")
    private String mobileNumber;

    @Size(min = 2, max = 255)
    @Schema(defaultValue = "test@gmail.com", type = "string" , description = "User email")
    @Email
    private String email;

    @Schema(defaultValue = "NOT_ACTIVE", type = "string" , description = "Status")
    private Status status = Status.NOT_ACTIVE;
}

package com.zagvladimir.controller.requests.users;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserCreateRequest {

    @NotBlank
    @Size(min = 2,max = 25)
    private String username;

    @NotBlank
    @Size(min = 2,max = 100)
    private String userLogin;

    @NotBlank
    @Size(min = 6,max = 200)
    private String userPassword;

    private long locationId;

    private String locationDetails;

    private String phoneNumber;

    private String mobileNumber;

    @Email
    private String email;

    private Status status = Status.ACTIVE;


}

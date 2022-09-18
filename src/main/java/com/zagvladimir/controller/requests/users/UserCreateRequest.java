package com.zagvladimir.controller.requests.users;

import com.zagvladimir.domain.Status;
import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;

    private String userLogin;

    private String userPassword;

    private int locationId;

    private String locationDetails;

    private String phoneNumber;

    private String mobileNumber;

    private String email;

    private Status status;


}

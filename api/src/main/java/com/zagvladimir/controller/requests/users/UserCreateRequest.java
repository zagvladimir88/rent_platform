package com.zagvladimir.controller.requests.users;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;

    private String userLogin;

    private String userPassword;

    private long locationId;

    private String locationDetails;

    private String phoneNumber;

    private String mobileNumber;

    private String email;

    private Status status = Status.ACTIVE;


}

package com.zagvladimir.controller.requests;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;

    private String user_password;

    private int location_id;

    private String location_details;

    private String phone_number;

    private String mobile_number;

    private String email;


}

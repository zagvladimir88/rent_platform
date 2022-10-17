package com.zagvladimir.controller.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    private Long id;

    private String username;

    private String userLogin;

    private LocationResponse userLocation;

    private String locationDetails;

    private String phoneNumber;

    private String mobileNumber;

    private String email;

    private Set<RoleResponse> userRoles;

}

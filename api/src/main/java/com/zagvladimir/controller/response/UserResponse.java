package com.zagvladimir.controller.response;

import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private CredentialsResponse credentials;

    private LocationResponse userLocation;

    private String locationDetails;

    private String mobileNumber;

    private Set<RoleResponse> userRoles;

}

package com.zagvladimir.dto.response.user;

import com.zagvladimir.dto.response.RoleResponse;
import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private CredentialsResponse credentials;

    private AddressResponse address;

    private String mobileNumber;

    private Set<RoleResponse> userRoles;

}

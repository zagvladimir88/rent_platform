package com.zagvladimir.dto;

import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.Status;
import lombok.*;


import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String userLogin;

    private String userPassword;

    private int locationId;

    private String locationDetails;

    private String phoneNumber;

    private String mobileNumber;

    private String email;

    private Timestamp registrationDate;

    private Timestamp creationDate;

    private Timestamp modificationDate;

    private Status status;

    private List<RoleDTO> roles;
}

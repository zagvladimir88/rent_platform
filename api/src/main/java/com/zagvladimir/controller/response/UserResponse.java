package com.zagvladimir.controller.response;

import com.zagvladimir.domain.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class UserResponse {

    private Long id;

    private String username;

    private String userLogin;

    private String userPassword;

    private Location location;

    private String locationDetails;

    private String phoneNumber;

    private String mobileNumber;

    private String email;

    private Timestamp registrationDate;

    private Set<Role> roles;

    private Set<Item> items;

    private Set<ItemLeased> itemsleased;

    private Set<Grade> gradesToSet;

    private Set<Grade> gradesFromSet;

}

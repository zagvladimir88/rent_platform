package com.zagvladimir.domain;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;

import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "username")
    private String username;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "location_id")
    private int locationId;
    @Column(name = "location_details")
    private String locationDetails;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

}

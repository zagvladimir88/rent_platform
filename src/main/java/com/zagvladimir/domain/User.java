package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;


import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"roles"})
@Table(name = "users")
public class User extends BaseEntity {

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


  @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("users")
  private Set<Role> roles;
}

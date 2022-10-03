package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;



import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"roles","items","itemsleased","gradesToSet","gradesFromSet"})
@Table(name = "users")
public class User extends BaseEntity {

  @Column(name = "username")
  private String username;

  @Column(name = "user_login")
  private String userLogin;

  @Column(name = "user_password")
  private String userPassword;

  @ManyToOne
  @JoinColumn(name = "location_id")
  @JsonBackReference(value = "user-location")
  private Location location;

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

  @OneToMany(mappedBy="owner", fetch = FetchType.EAGER)
  @JsonManagedReference(value="owner")
  private Set<Item> items;

  @OneToMany(mappedBy="renter", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<ItemLeased> itemsleased;

  @OneToMany(mappedBy="userTo", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Grade> gradesToSet;

  @OneToMany(mappedBy="userFrom", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Grade> gradesFromSet;
}

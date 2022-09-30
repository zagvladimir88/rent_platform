package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zagvladimir.domain.enums.Status;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;


import java.util.Date;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"roles","items","itemsleased"})
@Table(name = "users")
@AllArgsConstructor
public class User extends BaseEntity {

  public User() {
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "user_login")
  private String userLogin;

  @Column(name = "user_password")
  private String userPassword;

  @ManyToOne
  @JoinColumn(name = "location_id")
  @JsonManagedReference
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




  @Column(name = "creation_date")
  @JsonIgnore
  private Timestamp creationDate;

  @Column(name = "modification_date")
  @JsonIgnore
  private Timestamp modificationDate;

  @Column(name = "status")
  @Enumerated(value = EnumType.STRING)
  private Status status = Status.ACTIVE;




  @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("users")
  private Set<Role> roles;

  @OneToMany(mappedBy="owner", fetch = FetchType.EAGER)
  @JsonManagedReference
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

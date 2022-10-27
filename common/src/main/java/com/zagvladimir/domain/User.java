package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"roles","items","itemsLeased","grades"})
@Table(name = "users")
@ToString(exclude = {"location","roles","items","grades"})
public class User extends AuditingEntity {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;


  @Column(name = "user_login")
  private String userLogin;

  @Column(name = "user_password")
  private String userPassword;

  @Column(name = "location_details")
  private String locationDetails;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "registration_date")
  private Timestamp registrationDate;

  @Column(name = "activation_code")
  private String activationCode;

  @ManyToOne
  @JoinColumn(name = "location_id")
  @JsonBackReference
  private Location location;

  @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("users")
  private Set<Role> roles;

  @OneToMany(mappedBy="renter", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<ItemLeased> itemsLeased;

  @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Grade> grades;
}

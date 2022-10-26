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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"roles","items","itemsLeased","gradesToSet","gradesFromSet"})
@Table(name = "users")
@ToString(exclude = {"location","roles","items","gradesToSet","gradesFromSet"})
public class User extends AuditingEntity {

  @NotBlank
  @Size(min = 2,max = 25)
  @Column(name = "first_name")
  private String firstName;

  @NotBlank
  @Size(min = 2,max = 32)
  @Column(name = "last_name")
  private String lastName;

  @Size(min = 2,max = 100)
  @Column(name = "user_login")
  private String userLogin;

  @NotBlank()
  @Column(name = "user_password")
  private String userPassword;

  @Column(name = "location_details")
  private String locationDetails;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @Email
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

  @OneToMany(mappedBy="owner", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Item> items;

  @OneToMany(mappedBy="renter", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<ItemLeased> itemsLeased;

  @OneToMany(mappedBy="userTo", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Grade> gradesTo;

  @OneToMany(mappedBy="userFrom", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Grade> gradesFrom;
}

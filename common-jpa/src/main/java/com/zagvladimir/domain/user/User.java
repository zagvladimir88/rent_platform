package com.zagvladimir.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zagvladimir.domain.AuditingEntity;
import com.zagvladimir.domain.Grade;
import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"roles","items","itemsLeased","grades"})
@Table(name = "users")
@ToString(exclude = {"roles","items","grades"})
public class User extends AuditingEntity {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Embedded
  private Credentials credentials;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "addressLine1", column = @Column(name = "address_line1")),
          @AttributeOverride(name = "addressLine2", column = @Column(name = "address_line2")),
          @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
  })
  private Address address;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @Column(name = "registration_date")
  private Timestamp registrationDate;

  @Column(name = "activation_code")
  private String activationCode;

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

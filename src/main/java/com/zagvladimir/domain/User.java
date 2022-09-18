package com.zagvladimir.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;



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


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}

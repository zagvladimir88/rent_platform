package com.zagvladimir.domain.user;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String state;
    private String city;
    private String postalCode;

}

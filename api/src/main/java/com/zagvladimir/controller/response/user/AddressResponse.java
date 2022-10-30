package com.zagvladimir.controller.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    private String addressLine1;
    private String addressLine2;
    private String state;
    private String city;
    private String postalCode;

}

package com.zagvladimir.controller.requests.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UserChangeAddressRequest {
    @Size(min = 2,max = 200)
    @Schema(defaultValue = "Microdistrict 17", type = "string" , description = "User address first line")
    private String addressLine1;

    @Size(min = 2,max = 200)
    @Schema(defaultValue = "House 5-2", type = "string" , description = "User address second line")
    private String addressLine2;

    @Size(min = 2,max = 25)
    @Schema(defaultValue = "Gomelskaya", type = "string" , description = "State")
    private String state;

    @Size(min = 2,max = 25)
    @Schema(defaultValue = "Zhlobin", type = "string" , description = "City")
    private String city;

    @Size(min = 2,max = 10)
    @Schema(defaultValue = "247210", type = "string" , description = "Postal code")
    private String postalCode;
}

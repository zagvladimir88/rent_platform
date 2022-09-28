package com.zagvladimir.controller.requests.location;

import com.zagvladimir.domain.Country;
import com.zagvladimir.domain.Status;
import lombok.Data;

@Data
public class LocationCreateRequest {

    private String postalCode;
    private String name;
    private String description;
    private Country country;
    private Status status = Status.ACTIVE;

}

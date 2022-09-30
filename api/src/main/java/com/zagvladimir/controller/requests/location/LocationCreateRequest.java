package com.zagvladimir.controller.requests.location;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

@Data
public class LocationCreateRequest {

    private String postalCode;
    private String name;
    private String description;
    private long countryId;
    private Status status = Status.ACTIVE;

}

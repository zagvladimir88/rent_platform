package com.zagvladimir.controller.requests.location;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

@Data
public class LocationUpdateRequest {
    private String postalCode;
    private String name;
    private String description;
    private Long countryId;
    private Status status = Status.ACTIVE;
}

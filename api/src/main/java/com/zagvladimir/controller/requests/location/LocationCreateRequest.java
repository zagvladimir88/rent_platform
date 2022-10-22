package com.zagvladimir.controller.requests.location;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LocationCreateRequest {

    @Schema(defaultValue = "247210", type = "Integer" , description = "Postal code")
    private String postalCode;

    @Schema(defaultValue = "Zhlobin", type = "string" , description = "City name")
    private String name;

    @Schema(defaultValue = "Gomelskaya oblast'", type = "string" , description = "State")
    private String description;

    @Schema(defaultValue = "1", type = "Long" , description = "Country id")
    private long countryId;

    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status = Status.ACTIVE;

}

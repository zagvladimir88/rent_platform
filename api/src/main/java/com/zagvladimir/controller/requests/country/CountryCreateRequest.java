package com.zagvladimir.controller.requests.country;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CountryCreateRequest {

    @Schema(defaultValue = "Belarus", type = "string" , description = "Country name")
    @NotNull
    @Size(min = 2, max = 30)
    private String countryName;


    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status = Status.ACTIVE;
}

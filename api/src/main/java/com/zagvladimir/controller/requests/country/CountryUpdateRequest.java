package com.zagvladimir.controller.requests.country;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CountryUpdateRequest {

    @Schema(defaultValue = "Germany", type = "string" , description = "Country name")
    @NotBlank
    private String countryName;

    @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
    private Status status;
}
